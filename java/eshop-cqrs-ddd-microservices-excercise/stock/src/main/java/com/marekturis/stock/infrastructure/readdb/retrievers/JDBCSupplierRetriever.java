package com.marekturis.stock.infrastructure.readdb.retrievers;

import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCConnectionProvider;
import com.marekturis.stock.infrastructure.persistence.ReadDbJDBCOptions;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.ProductSuppliedBySupplierDto;
import com.marekturis.stock.infrastructure.readdb.retrievers.dtos.SupplierDto;

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
public class JDBCSupplierRetriever {

	private final JDBCConnectionProvider connectionProvider;

	public JDBCSupplierRetriever(ReadDbJDBCOptions jdbcOptions) {
		this.connectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	public List<SupplierDto> getAllSuppliers() {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM suppliers");
			ResultSet resultSet = statement.executeQuery();

			List<SupplierDto> suppliers = new ArrayList<>();
			while(resultSet.next()) {
				suppliers.add(new SupplierDto(
						resultSet.getInt("id"),
						resultSet.getString("name")));
			}

			return suppliers;
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}

	public List<ProductSuppliedBySupplierDto> getProductsSuppliedBySupplier(int supplierId) {
		try (Connection connection = connectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM suppliers");
			ResultSet resultSet = statement.executeQuery();

			List<ProductSuppliedBySupplierDto> productsSuppliedBySupplier = new ArrayList<>();
			while(resultSet.next()) {
				productsSuppliedBySupplier.add(new ProductSuppliedBySupplierDto(
						resultSet.getInt("id")));
			}

			return productsSuppliedBySupplier;
		} catch (SQLException e) {
			throw new PersistanceException("Exception while executing sql statement", e);
		}
	}
}
