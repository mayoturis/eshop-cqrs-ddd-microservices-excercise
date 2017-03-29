package com.marekturis.common.infrastructure.persistance.jdbc;

import com.marekturis.common.application.eventforwarding.LatestForwardedEventsProvider;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Named;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Marek Turis
 */
@Named
public class JDBCLatestForwardedEventsProvider extends JDBCPersistenceStore implements LatestForwardedEventsProvider {

	public JDBCLatestForwardedEventsProvider(JDBCOptions jdbcOptions) {
		super(jdbcOptions);
	}

	@Override
	public Date latestForwardedEvents() {
		try (Connection connection = getConnection()) {
			PreparedStatement eventStatement = connection.prepareStatement(
					"SELECT * FROM event_forwarding");
			ResultSet eventResultSet = eventStatement.executeQuery();
			eventResultSet.next();
			return new Date(eventResultSet.getTimestamp("latest_time_event_forwarded").getTime());
		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	@Override
	public void setLatestForwardedEvents(Date date) {
		try (Connection connection = getConnection()) {
			PreparedStatement eventStatement = connection.prepareStatement(
					"UPDATE event_forwarding SET latest_time_event_forwarded = ?");
			eventStatement.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
			eventStatement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}
}
