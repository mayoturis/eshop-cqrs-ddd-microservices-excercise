package com.marekturis.stock.infrastructure;

import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.common.application.command.CommandHandlerBuilder;
import com.marekturis.common.application.transaction.TransactionUnit;
import com.marekturis.common.infrastructure.CommonConfig;
import com.marekturis.stock.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Marek Turis
 */
@Configuration
@ComponentScan({"com.marekturis.stock.application",
		"com.marekturis.stock.domain", "com.marekturis.stock.infrastructure"})
@Import({CommonConfig.class})
public class StockConfig {
	@Autowired
	private CreateCounterCommandHandler createCounterCommandHandler;

	@Autowired
	private IncreaseCounterCommandHandler increaseCounterCommandHandler;

	@Autowired
	private SkuskaCommandHandler skuskaCommandHandler;

	@Autowired
	private CommandHandlerBuilder commandHandlerBuilder;

	@Bean
	public CommandDispatcher commandDispatcher() {
		CommandDispatcher commandDispatcher = new CommandDispatcher(commandHandlerBuilder);
		commandDispatcher.addHandler(CreateCounterCommand.class, createCounterCommandHandler);
		commandDispatcher.addHandler(IncreaseCounterCommand.class, increaseCounterCommandHandler);
		commandDispatcher.addHandler(SkuskaCommand.class, skuskaCommandHandler);
		return commandDispatcher;
	}
}
