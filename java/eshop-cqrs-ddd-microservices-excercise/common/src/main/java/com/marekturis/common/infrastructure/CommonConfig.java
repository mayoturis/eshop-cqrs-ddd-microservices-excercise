package com.marekturis.common.infrastructure;

import com.marekturis.common.infrastructure.persistance.jdbc.JDBCOptions;
import com.marekturis.common.infrastructure.persistance.jdbc.JDBCPersistenceStore;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author Marek Turis
 */
@Configuration
@ComponentScan(value = "com.marekturis.common"/*, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.marekturis.common.infrastructure.persistance.jdbc.*")
}*/)
public class CommonConfig {
}
