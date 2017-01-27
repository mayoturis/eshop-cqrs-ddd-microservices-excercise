package com.marekturis.identity.infrastructure;

import com.marekturis.common.infrastructure.CommonConfig;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Application context configuration.
 *
 * @author Marek Turis
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({"com.marekturis.identity.application",
		"com.marekturis.identity.domain", "com.marekturis.identity.infrastructure"})
@Import(CommonConfig.class)
public class IdentityConfig {

	@Bean
	public JpaTransactionManager transactionManager() throws IOException {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
		LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean ();
		jpaFactoryBean.setDataSource(db());
		jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
		jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return jpaFactoryBean;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(){
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public LoadTimeWeaver instrumentationLoadTimeWeaver() {
		return new InstrumentationLoadTimeWeaver();
	}

	@Bean
	public DataSource db() throws IOException {
		MysqlDataSource dataSource = new MysqlDataSource();
		Properties properties = new Properties();
		properties.load(new ClassPathResource("config.properties").getInputStream());
		String dbUser = properties.getProperty("DB_USER");
		String dbPassword = properties.getProperty("DB_PASSWORD");
		String dbHost = properties.getProperty("DB_HOST");
		dataSource.setUrl(dbHost);
		dataSource.setUser(dbUser);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}
}
