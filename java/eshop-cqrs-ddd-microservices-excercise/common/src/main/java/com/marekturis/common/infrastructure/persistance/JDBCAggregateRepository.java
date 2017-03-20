package com.marekturis.common.infrastructure.persistance;

import com.marekturis.common.application.transaction.AggregateRootChangesTransactionUnit;
import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.common.domain.event.AggregateEventStore;
import com.marekturis.common.domain.repository.AggregateRepository;
import org.springframework.util.SerializationUtils;

import javax.inject.Named;
import java.sql.*;

/**
 * @author Marek Turis
 */
@Named
public class JDBCAggregateRepository extends JDBCPersistenceStore implements AggregateRepository {

	private AggregateEventStore eventStore;
	private AggregateRootChangesTransactionUnit aggregateRootChangesTransactionUnit;

	public JDBCAggregateRepository(JDBCOptions jdbcOptions, AggregateEventStore eventStore, AggregateRootChangesTransactionUnit aggregateRootChangesTransactionUnit) {
		super(jdbcOptions);
		this.eventStore = eventStore;
		this.aggregateRootChangesTransactionUnit = aggregateRootChangesTransactionUnit;
	}

	@Override
	public <TAggregateRoot extends AggregateRoot> TAggregateRoot getById(Integer id) {
		try(Connection conn = getConnection()){
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM aggregates WHERE id = ?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSetRowCount(resultSet) == 0) {
				return null;
			}

			resultSet.next();
			int snapshotVersion = resultSet.getInt("snapshot_version");
			byte[] snapshot = resultSet.getBytes("snapshot");
			int version = resultSet.getInt("version");

			TAggregateRoot aggregate = buildAggregateInSnapshotState(snapshot);
			aggregate.setVersions(version);

			for (AggregateEvent event : eventStore.getEvents(id, snapshotVersion)) {
				aggregate.replayEvent(event);
			}

			aggregateRootChangesTransactionUnit.trackAggregate(aggregate);
			return aggregate;

		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	private int resultSetRowCount(ResultSet resultSet) throws SQLException {
		resultSet.last();
		int count = resultSet.getRow();
		resultSet.beforeFirst();
		return count;
	}

	private <TAggregateRoot extends AggregateRoot> TAggregateRoot buildAggregateInSnapshotState(byte[] snapshot) {
		return (TAggregateRoot) SerializationUtils.deserialize(snapshot);
	}

	@Override
	public void add(AggregateRoot aggregateRoot){
		try(Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO aggregates (id, version, snapshot, snapshot_version) VALUES (?,?,?,?)");
			statement.setInt(1, aggregateRoot.identity());
			statement.setInt(2, aggregateRoot.currentVersion());
			statement.setBytes(3, serializeObject(aggregateRoot));
			statement.setInt(4, aggregateRoot.currentVersion());
			statement.executeUpdate();

			eventStore.add(aggregateRoot.pullChanges());
			aggregateRootChangesTransactionUnit.trackAggregate(aggregateRoot);

		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}
}
