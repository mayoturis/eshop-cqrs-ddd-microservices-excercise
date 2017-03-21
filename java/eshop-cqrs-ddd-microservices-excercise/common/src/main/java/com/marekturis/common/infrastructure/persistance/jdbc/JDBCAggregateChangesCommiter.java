package com.marekturis.common.infrastructure.persistance.jdbc;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.AggregateEventStore;
import com.marekturis.common.domain.repository.AggregateChangesCommiter;
import com.marekturis.common.infrastructure.persistance.OptimisticLockException;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import org.springframework.util.SerializationUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.*;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class JDBCAggregateChangesCommiter extends JDBCPersistenceStore implements AggregateChangesCommiter {

	private static final int NUMBER_OF_NEW_EVENTS_TO_CREATE_SNAPSHOT = 5;

	private AggregateEventStore eventStore;

	@Inject
	public JDBCAggregateChangesCommiter(JDBCOptions jdbcOptions, AggregateEventStore eventStore) {
		super(jdbcOptions);
		this.eventStore = eventStore;
	}

	@Override
	public void commitChanges(AggregateRoot aggregateRoot) {
		List<AggregateEvent> changes = aggregateRoot.pullChanges();
		if (changes.size() == 0) {
			return;
		}

		RawAggregateInDb rawAggregateInDb = getRawAggregateInDb(aggregateRoot);
		checkOptimisticLockVersion(aggregateRoot, rawAggregateInDb);
		eventStore.add(changes);
		updateVersion(aggregateRoot);
		createSnapshotIfNeeded(aggregateRoot, rawAggregateInDb);
	}

	private RawAggregateInDb getRawAggregateInDb(AggregateRoot aggregateRoot) {
		try {
			return tryGetRawAggregateInDb(aggregateRoot);
		} catch (SQLException ex) {
			throw new PersistanceException(ex);
		}
	}

	private RawAggregateInDb tryGetRawAggregateInDb(AggregateRoot aggregateRoot) throws SQLException {
		try(Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement("SELECT id, version, snapshot_version FROM aggregates WHERE id = ?");
			statement.setInt(1, aggregateRoot.identity());
			ResultSet resultSet = statement.executeQuery();
			return RawAggregateInDb.fromResultSet(resultSet);
		}
	}

	private void checkOptimisticLockVersion(AggregateRoot aggregateRoot, RawAggregateInDb rawAggregateInDb) {
		int aggregateVersionInStore = rawAggregateInDb.getVersion();

		if (aggregateVersionInStore != aggregateRoot.versionWhenLoaded()) {
			throw new OptimisticLockException("Aggregate with id " + aggregateRoot.identity() +
					" was modified in another thread");
		}
	}

	private void updateVersion(AggregateRoot aggregateRoot) {
		try {
			tryUpdateVersion(aggregateRoot);
		} catch (SQLException ex) {
			throw new PersistanceException(ex);
		}
	}

	private void tryUpdateVersion(AggregateRoot aggregateRoot) throws SQLException {
		try(Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement(
					"UPDATE aggregates SET version = ? WHERE id = ?");
			statement.setInt(1, aggregateRoot.currentVersion());
			statement.setInt(2, aggregateRoot.identity());
			statement.executeUpdate();
		}
	}

	private void createSnapshotIfNeeded(AggregateRoot aggregateRoot, RawAggregateInDb rawAggregateInDb) {
		if (aggregateRoot.currentVersion() - rawAggregateInDb.getSnapshotVersion() < NUMBER_OF_NEW_EVENTS_TO_CREATE_SNAPSHOT) {
			return;
		}

		createSnapshot(aggregateRoot);
	}

	private void createSnapshot(AggregateRoot aggregateRoot) {
		try {
			tryCreateSnapshot(aggregateRoot);
		} catch (SQLException e) {
			throw new PersistanceException(e);
		}
	}

	private void tryCreateSnapshot(AggregateRoot aggregateRoot) throws SQLException {
		try(Connection conn = getConnection()) {
			PreparedStatement statement = conn.prepareStatement(
					"UPDATE aggregates SET snapshot = ?, snapshot_version = ? WHERE id = ?");
			statement.setBytes(1, serializeObject(aggregateRoot));
			statement.setInt(2, aggregateRoot.currentVersion());
			statement.setInt(3, aggregateRoot.identity());
			statement.executeUpdate();
		}
	}

	private static class RawAggregateInDb {
		private int id;
		private int version;
		private int snapshotVersion;

		private RawAggregateInDb(int id, int version, int snapshotVersion) {
			this.id = id;
			this.version = version;
			this.snapshotVersion = snapshotVersion;
		}

		public int getId() {
			return id;
		}

		public int getVersion() {
			return version;
		}

		public int getSnapshotVersion() {
			return snapshotVersion;
		}

		public static RawAggregateInDb fromResultSet(ResultSet resultSet) throws SQLException {
			resultSet.next();
			return new RawAggregateInDb(
					resultSet.getInt("id"),
					resultSet.getInt("version"),
					resultSet.getInt("snapshot_version"));
		}
	}
}
