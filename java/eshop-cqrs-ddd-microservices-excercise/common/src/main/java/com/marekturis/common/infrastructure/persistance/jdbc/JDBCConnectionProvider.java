package com.marekturis.common.infrastructure.persistance.jdbc;

import com.marekturis.common.infrastructure.persistance.PersistanceException;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCOptions;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Marek Turis
 */
public class JDBCConnectionProvider {

	private JDBCOptions jdbcOptions;

	public JDBCConnectionProvider(JDBCOptions jdbcOptions) {
		this.jdbcOptions = jdbcOptions;
	}

	public Connection getConnection() {
		try {
			Class.forName(jdbcOptions.driverName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			return DriverManager.getConnection(jdbcOptions.getHost(), jdbcOptions.getUser(), jdbcOptions.getPassword());
		} catch (SQLException e) {
			throw new PersistanceException("Problem while aquiring connection", e);
		}
	}
}
