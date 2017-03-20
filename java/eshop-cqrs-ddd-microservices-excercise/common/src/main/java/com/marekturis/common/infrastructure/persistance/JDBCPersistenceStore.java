package com.marekturis.common.infrastructure.persistance;

import org.springframework.util.SerializationUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
public class JDBCPersistenceStore {

	private JDBCOptions jdbcOptions;

	public JDBCPersistenceStore(JDBCOptions jdbcOptions) {
		this.jdbcOptions = jdbcOptions;
	}

	protected byte[] serializeObject(Object object) {
		return SerializationUtils.serialize(object);
	}

	protected Connection getConnection() throws SQLException {
		try {
			Class.forName(jdbcOptions.driverName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return DriverManager.getConnection(jdbcOptions.getHost(), jdbcOptions.getUser(), jdbcOptions.getPassword());
	}
}
