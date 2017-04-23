package com.marekturis.common.infrastructure.persistance.jdbc;

import org.springframework.util.SerializationUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
public class JDBCPersistenceStore {

	private JDBCConnectionProvider jdbcConnectionProvider;

	public JDBCPersistenceStore(JDBCOptions jdbcOptions) {
		jdbcConnectionProvider = new JDBCConnectionProvider(jdbcOptions);
	}

	protected byte[] serializeObject(Object object) {
		return SerializationUtils.serialize(object);
	}

	protected Connection getConnection() {
		return jdbcConnectionProvider.getConnection();
	}
}
