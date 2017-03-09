package com.marekturis.common.infrastructure.persistance;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventStore;
import com.marekturis.common.domain.repository.AggregateChangesCommiter;
import org.springframework.util.SerializationUtils;

import javax.inject.Named;
import java.sql.*;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class JDBCAggregateChangesCommiter implements AggregateChangesCommiter {

	private JDBCOptions jdbcOptions;
	private EventStore eventStore;

	public JDBCAggregateChangesCommiter(JDBCOptions jdbcOptions, EventStore eventStore) {
		this.jdbcOptions = jdbcOptions;
		this.eventStore = eventStore;
	}

	@Override
	public void commitChanges(AggregateRoot aggregateRoot) {
		int aggregateVersionInStore = getAggregateVersionInStore(aggregateRoot);

		if (aggregateVersionInStore != aggregateRoot.versionWhenLoaded()) {
			throw new OptimisticLockException("Aggregate with id " + aggregateRoot.identity() +
					" was modified in another thread");
		}
		// todo do snapshot
		eventStore.add(aggregateRoot.changes(), aggregateRoot.identity());
	}

	private int getAggregateVersionInStore(AggregateRoot aggregateRoot) {
		try(Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT version FROM aggregates WHERE id = ?");
			statement.setInt(1, aggregateRoot.identity());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt("version");
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
