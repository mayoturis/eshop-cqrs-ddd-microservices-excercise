package com.marekturis.stock.infrastructure.readdb.retrievers;

import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.ProductInWarehouseDto;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.WarehouseDto;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class JDBCWarehouseRetriever {
	private final JDBCConnectionProvider connectionProvider;

	public JDBCWarehouseRetriever(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	public List<WarehouseDto> getAllWarehouses() {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM warehouses");
			ResultSet resultSet = statement.executeQuery();

			List<WarehouseDto> warehouses = new ArrayList<>();
			while(resultSet.next()) {
				warehouses.add(new WarehouseDto(
						resultSet.getInt("id"),
						resultSet.getString("location")));
			}

			return warehouses;
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}

	public List<ProductInWarehouseDto> getAllProductsInWarehouse(int warehouseId) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM warehouse_product WHERE warehouse_id = ?");
			statement.setInt(1, warehouseId);
			ResultSet resultSet = statement.executeQuery();

			List<ProductInWarehouseDto> productInWarehouses = new ArrayList<>();
			while(resultSet.next()) {
				productInWarehouses.add(
						new ProductInWarehouseDto(
								resultSet.getInt("product_id"),
								resultSet.getInt("ammount")));
			}

			return productInWarehouses;
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
