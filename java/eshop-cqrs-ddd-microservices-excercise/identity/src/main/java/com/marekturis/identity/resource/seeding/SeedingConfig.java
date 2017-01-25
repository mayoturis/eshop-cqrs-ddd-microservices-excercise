package com.marekturis.identity.resource.seeding;

import com.marekturis.identity.infrastructure.IdentityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Marek Turis
 */
@Configuration
@Import(IdentityConfig.class)
@ComponentScan(basePackageClasses = DataSeeder.class)
public class SeedingConfig {

	@Inject
	private DataSeeder seeder;

	@PostConstruct
	public void dataLoading() {
		seeder.seedData();
	}
}
