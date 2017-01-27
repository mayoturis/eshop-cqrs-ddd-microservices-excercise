package com.marekturis.common.infrastructure.persistance;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.repository.BasicRepository;
import org.springframework.util.SerializationUtils;

import java.sql.*;

/**
 * @author Marek Turis
 */
public class JDBCBasicRepository<TAggregateRoot extends AggregateRoot> implements BasicRepository<TAggregateRoot> {

	private JDBCOptions jdbcOptions;

	public JDBCBasicRepository(JDBCOptions jdbcOptions) {
		this.jdbcOptions = jdbcOptions;
	}

	@Override
	public TAggregateRoot getById(Integer id) {
		try(Connection conn = getConnection()){
			// todo start transaction
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

	private TAggregateRoot buildAggregateInSnapshotState(byte[] snapshot) {
		return (TAggregateRoot) SerializationUtils.deserialize(snapshot);
	}

	private Event buildEvent(byte[] data) {
		return (Event) SerializationUtils.deserialize(data);
	}

	@Override
	public void add(TAggregateRoot tAggregateRoot) {
		try(Connection conn = getConnection()) {
			// todo start transaction
			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO aggregates (id, version, snapshot, snapshot_version) VALUES (?,?,?,?)");
			statement.setInt(1, tAggregateRoot.identity());
			statement.setInt(2, tAggregateRoot.currentVersion());
			statement.setBytes(3, serializeObject(tAggregateRoot));
			statement.setInt(4, tAggregateRoot.currentVersion());
			statement.executeQuery();

			for (Event event : tAggregateRoot.changes()) {
				PreparedStatement eventStatement = conn.prepareStatement(
						"INSERT INTO events (aggregate_id, data, version) VALUES (?,?,?)");
				eventStatement.setInt(1, tAggregateRoot.identity());
				eventStatement.setBytes(2, serializeObject(event));
				eventStatement.setInt(3, event.version());
				eventStatement.executeQuery();
			}

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
