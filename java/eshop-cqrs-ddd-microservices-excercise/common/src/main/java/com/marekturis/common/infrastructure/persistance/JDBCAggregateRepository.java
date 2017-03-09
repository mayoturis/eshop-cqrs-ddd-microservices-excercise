package com.marekturis.common.infrastructure.persistance;

import com.marekturis.common.application.transaction.AggregateRootChangesTransactionUnit;
import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventStore;
import com.marekturis.common.domain.repository.AggregateRepository;
import org.springframework.util.SerializationUtils;

import javax.inject.Named;
import java.sql.*;

/**
 * @author Marek Turis
 */
@Named
public class JDBCAggregateRepository implements AggregateRepository {

	private JDBCOptions jdbcOptions;
	private EventStore eventStore;
	private AggregateRootChangesTransactionUnit aggregateRootChangesTransactionUnit;

	public JDBCAggregateRepository(JDBCOptions jdbcOptions, EventStore eventStore, AggregateRootChangesTransactionUnit aggregateRootChangesTransactionUnit) {
		this.jdbcOptions = jdbcOptions;
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
			TAggregateRoot aggregate = buildAggregateInSnapshotState(snapshot);

			PreparedStatement eventStatement = conn.prepareStatement(
					"SELECT * FROM events WHERE aggregate_id = ? AND version > ? ORDER BY version ASC");
			eventStatement.setInt(1, id);
			eventStatement.setInt(2, snapshotVersion);
			ResultSet eventResultSet = eventStatement.executeQuery();

			while (eventResultSet.next()) {
				Event event = buildEvent(eventResultSet.getBytes("data"));
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

	private Event buildEvent(byte[] data) {
		return (Event) SerializationUtils.deserialize(data);
	}

	@Override
	public void add(AggregateRoot aggregetRoot){
		try(Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO aggregates (id, version, snapshot, snapshot_version) VALUES (?,?,?,?)");
			statement.setInt(1, aggregetRoot.identity());
			statement.setInt(2, aggregetRoot.currentVersion());
			statement.setBytes(3, serializeObject(aggregetRoot));
			statement.setInt(4, aggregetRoot.currentVersion());
			statement.executeQuery();

			eventStore.add(aggregetRoot.changes(), aggregetRoot.identity());
			aggregateRootChangesTransactionUnit.trackAggregate(aggregetRoot);

		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	private byte[] serializeObject(Object object) {
		return SerializationUtils.serialize(object);
	}

	protected Connection getConnection() throws SQLException {
		try {
			Class.forName(jdbcOptions.driverName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return DriverManager.getConnection(jdbcOptions.getHost(), jdbcOptions.getUser(), jdbcOptions.getUser());
	}
}
