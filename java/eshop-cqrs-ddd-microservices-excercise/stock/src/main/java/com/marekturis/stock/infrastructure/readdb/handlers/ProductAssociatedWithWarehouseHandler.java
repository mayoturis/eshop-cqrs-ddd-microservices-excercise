package com.marekturis.stock.infrastructure.readdb.handlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.domain.warehouse.ProductAssociatedWithWarehouse;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
@Named
public class ProductAssociatedWithWarehouseHandler implements EventHandler {

	private final JDBCConnectionProvider connectionProvider;

	public ProductAssociatedWithWarehouseHandler(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	@Override
	public String eventToHandle() {
		return ProductAssociatedWithWarehouse.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE warehouses SET warehouse_product_id = ?  WHERE id = ?");
			statement.setInt(1, event.getInt("productId"));
			statement.setInt(2, event.getInt("warehouseId"));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
