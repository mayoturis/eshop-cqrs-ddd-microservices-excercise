package com.marekturis.stock.infrastructure.persistence;

import com.marekturis.common.infrastructure.persistance.jdbc.JDBCOptions;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import javax.inject.Named;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Marek Turis
 */
@Primary
@Named
public class DefaultJDBCOptions implements JDBCOptions {

	private String dbHost;
	private String dbUser;
	private String dbPassword;
	private String dbDriverName;

	public DefaultJDBCOptions() {
		Properties properties = new Properties();
		try {
			properties.load(new ClassPathResource("config.properties").getInputStream());
		} catch (IOException e) {
			throw new RuntimeException("JDBC configuration in config.properties could not be loaded", e);
		}
		dbHost = properties.getProperty("DB_HOST");
		dbUser = properties.getProperty("DB_USER");
		dbPassword = properties.getProperty("DB_PASSWORD");
		dbDriverName = properties.getProperty("DB_DRIVER_NAME");
	}
	
	public String getHost() {
		return dbHost;
	}

	public String getUser() {
		return dbUser;
	}

	public String getPassword() {
		return dbPassword;
	}

	public String driverName() {
		return dbDriverName;
	}
}
