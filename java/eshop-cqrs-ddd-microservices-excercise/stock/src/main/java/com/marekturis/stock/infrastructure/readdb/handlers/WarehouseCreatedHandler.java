package com.marekturis.stock.infrastructure.readdb.handlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.domain.warehouse.WarehouseCreated;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
@Named
public class WarehouseCreatedHandler implements EventHandler {

	private final JDBCConnectionProvider connectionProvider;

	public WarehouseCreatedHandler(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	@Override
	public String eventToHandle() {
		return WarehouseCreated.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO warehouses (id, location) VALUES (?,?)");
			statement.setInt(1, event.getInt("id"));
			statement.setString(2, event.getString("location"));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
