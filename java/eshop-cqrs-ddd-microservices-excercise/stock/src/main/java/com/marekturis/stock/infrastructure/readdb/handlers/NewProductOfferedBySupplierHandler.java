package com.marekturis.stock.infrastructure.readdb.handlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.domain.supplier.NewProductOfferedBySupplier;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
@Named
public class NewProductOfferedBySupplierHandler implements EventHandler {

	private final JDBCConnectionProvider connectionProvider;

	public NewProductOfferedBySupplierHandler(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	@Override
	public String eventToHandle() {
		return NewProductOfferedBySupplier.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO supplier_product (product_id, supplier_id, price) VALUES (?,?,?)");
			statement.setInt(1, event.getInt("productId"));
			statement.setInt(2, event.getInt("supplierId"));
			statement.setDouble(3, event.getDouble("price"));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
