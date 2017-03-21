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
	
	public String getHost() {
		return getResource("DB_HOST");
	}

	public String getUser() {
		return getResource("DB_USER");
	}

	public String getPassword() {
		return getResource("DB_PASSWORD");
	}

	public String driverName() {
		return getResource("DB_DRIVER_NAME");
	}

	private String getResource(String name) {
		Properties properties = new Properties();
		try {
			properties.load(new ClassPathResource("config.properties").getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties.getProperty(name);
	}
}
