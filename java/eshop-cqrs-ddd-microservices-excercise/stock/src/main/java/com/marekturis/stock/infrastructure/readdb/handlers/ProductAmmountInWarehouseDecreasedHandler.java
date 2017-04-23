package com.marekturis.stock.infrastructure.readdb.handlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.domain.warehouse.ProductAmmountInWarehouseDecreased;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
@Named
public class ProductAmmountInWarehouseDecreasedHandler implements EventHandler {

	private final JDBCConnectionProvider connectionProvider;

	public ProductAmmountInWarehouseDecreasedHandler(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	@Override
	public String eventToHandle() {
		return ProductAmmountInWarehouseDecreased.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE warehouse_product SET ammount = ammount - ? " +
							" WHERE product_id = ? AND warehouse_id = ?");
			statement.setInt(1, event.getInt("ammount"));
			statement.setInt(2, event.getInt("productId"));
			statement.setInt(3, event.getInt("warehouseId"));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
