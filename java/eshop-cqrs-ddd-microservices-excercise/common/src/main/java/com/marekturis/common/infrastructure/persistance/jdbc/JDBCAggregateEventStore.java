package com.marekturis.common.infrastructure.persistance.jdbc;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.AggregateEventStore;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import org.springframework.util.SerializationUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class JDBCAggregateEventStore extends JDBCPersistenceStore implements AggregateEventStore {

	@Inject
	public JDBCAggregateEventStore(JDBCOptions jdbcOptions) {
		super(jdbcOptions);
	}

	@Override
	public void add(AggregateEvent event) {
		try {
			tryAdd(event);
		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	private void tryAdd(AggregateEvent event) throws SQLException {
		try(Connection conn = getConnection()) {
			PreparedStatement eventStatement = conn.prepareStatement(
					"INSERT INTO events (aggregate_id, data, aggregate_version) VALUES (?,?,?)");
			eventStatement.setInt(1, event.aggregateIdentity());
			eventStatement.setBytes(2, serializeObject(event));
			eventStatement.setInt(3, event.aggregateVersion());
			eventStatement.executeUpdate();
		}
	}

	@Override
	public void add(List<AggregateEvent> events) {
		for (AggregateEvent event : events) {
			add(event);
		}
	}

	@Override
	public List<AggregateEvent> getEvents(int aggregateId, int fromAggregateVersion) {
		try {
			return tryGetEvents(aggregateId, fromAggregateVersion);
		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	private List<AggregateEvent> tryGetEvents(int aggregateId, int fromAggregateVersion) throws SQLException {
		try(Connection conn = getConnection()) {
			PreparedStatement eventStatement = conn.prepareStatement(
					"SELECT * FROM events WHERE aggregate_id = ? AND aggregate_version > ? ORDER BY aggregate_version ASC");
			eventStatement.setInt(1, aggregateId);
			eventStatement.setInt(2, fromAggregateVersion);
			ResultSet eventResultSet = eventStatement.executeQuery();

			return aggregateEventsFromResultSet(eventResultSet);
		}
	}

	private List<AggregateEvent> aggregateEventsFromResultSet(ResultSet eventResultSet) throws SQLException {
		List<AggregateEvent> events = new ArrayList<>();
		while (eventResultSet.next()) {
			AggregateEvent event = buildEvent(eventResultSet.getBytes("data"));
			events.add(event);
		}

		return events;
	}

	private AggregateEvent buildEvent(byte[] data) {
		return (AggregateEvent) SerializationUtils.deserialize(data);
	}
}
