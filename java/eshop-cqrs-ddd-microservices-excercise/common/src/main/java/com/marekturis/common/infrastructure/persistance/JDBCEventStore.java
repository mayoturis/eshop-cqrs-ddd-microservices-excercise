package com.marekturis.common.infrastructure.persistance;

import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventStore;
import org.springframework.util.SerializationUtils;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class JDBCEventStore implements EventStore {

	private JDBCOptions jdbcOptions;

	public JDBCEventStore(JDBCOptions jdbcOptions) {
		this.jdbcOptions = jdbcOptions;
	}

	@Override
	public void add(Event event, int aggregateId) {
		try(Connection conn = getConnection()) {
			PreparedStatement eventStatement = conn.prepareStatement(
					"INSERT INTO events (aggregate_id, data, version) VALUES (?,?,?)");
			eventStatement.setInt(1, aggregateId);
			eventStatement.setBytes(2, serializeObject(event));
			eventStatement.setInt(3, event.version());
			eventStatement.executeQuery();

		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	@Override
	public void add(List<Event> events, int aggregateId) {
		for (Event event : events) {
			add(event, aggregateId);
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
