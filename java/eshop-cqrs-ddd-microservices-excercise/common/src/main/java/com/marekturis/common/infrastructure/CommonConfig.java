package com.marekturis.common.infrastructure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Marek Turis
 */
@Configuration
@ComponentScan(value = "com.marekturis.common")
@EnableAspectJAutoProxy
public class CommonConfig {
}
