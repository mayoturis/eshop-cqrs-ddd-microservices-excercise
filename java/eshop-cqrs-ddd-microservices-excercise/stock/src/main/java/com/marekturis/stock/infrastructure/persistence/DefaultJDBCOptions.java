package com.marekturis.stock.infrastructure.persistence;

import com.marekturis.common.infrastructure.persistance.JDBCOptions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Marek Turis
 */
public class DefaultJDBCOptions implements JDBCOptions {
	public String getHost() {
		return null;
	}

	public String getUser() {
		return null;
	}

	public String getPassword() {
		return null;
	}

	public String driverName() {
		return "com.mysql.jdbc.Driver";
	}

	private String getResource(String name) {
		Properties properties = new Properties();
		try {
			properties.load(new ClassPathResource("config.properties").getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties.getProperty("DB_USER");
	}
}
