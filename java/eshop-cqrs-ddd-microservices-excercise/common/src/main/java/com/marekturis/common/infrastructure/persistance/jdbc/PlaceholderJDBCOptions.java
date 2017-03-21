package com.marekturis.common.infrastructure.persistance.jdbc;


import javax.inject.Named;

/**
 * @author Marek Turis
 *
 * Created only for spring to be able to inject something
 */
@Named
public class PlaceholderJDBCOptions implements JDBCOptions {
	@Override
	public String getHost() {
		throw new UnsupportedOperationException("This class is not supposed to be used");
	}

	@Override
	public String getUser() {
		throw new UnsupportedOperationException("This class is not supposed to be used");
	}

	@Override
	public String getPassword() {
		throw new UnsupportedOperationException("This class is not supposed to be used");
	}

	@Override
	public String driverName() {
		throw new UnsupportedOperationException("This class is not supposed to be used");
	}
}
