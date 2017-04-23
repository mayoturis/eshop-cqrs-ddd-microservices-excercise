package com.marekturis.stock.infrastructure.readdb.handlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.domain.supplier.OfferedProductRemovedFromSupplier;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
@Named
public class OfferedProductRemovedFromSupplierHandler implements EventHandler {

	private final JDBCConnectionProvider connectionProvider;

	public OfferedProductRemovedFromSupplierHandler(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	@Override
	public String eventToHandle() {
		return OfferedProductRemovedFromSupplier.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM supplier_product WHERE supplier_id = ? AND product_id = ?");
			statement.setInt(1, event.getInt("supplierId"));
			statement.setInt(2, event.getInt("productId"));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
