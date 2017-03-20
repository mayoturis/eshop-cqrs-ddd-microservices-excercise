package com.marekturis.stock.infrastructure;

import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.common.application.transaction.TransactionUnit;
import com.marekturis.common.infrastructure.CommonConfig;
import com.marekturis.stock.application.CreateCounterCommand;
import com.marekturis.stock.application.CreateCounterCommandHandler;
import com.marekturis.stock.application.IncreaseCounterCommand;
import com.marekturis.stock.application.IncreaseCounterCommandHandler;
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
@Import(CommonConfig.class)
public class StockConfig {
	@Autowired
	private CreateCounterCommandHandler createCounterCommandHandler;

	@Autowired
	private IncreaseCounterCommandHandler increaseCounterCommandHandler;

	@Autowired
	private TransactionUnit transactionUnit;

	@Bean
	public CommandDispatcher commandDispatcher() {
		CommandDispatcher commandDispatcher = new CommandDispatcher(transactionUnit);
		commandDispatcher.addHandler(CreateCounterCommand.class, createCounterCommandHandler);
		commandDispatcher.addHandler(IncreaseCounterCommand.class, increaseCounterCommandHandler);
		return commandDispatcher;
	}
}
