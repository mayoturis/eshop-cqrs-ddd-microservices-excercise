package com.marekturis.common.infrastructure.persistance.jdbc;

/**
 * @author Marek Turis
 */
public interface JDBCOptions {
	String getHost();
	String getUser();
	String getPassword();
	String driverName();
}
