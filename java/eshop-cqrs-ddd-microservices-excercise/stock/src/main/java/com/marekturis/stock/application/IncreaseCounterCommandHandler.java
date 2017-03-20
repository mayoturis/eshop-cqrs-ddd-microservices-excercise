package com.marekturis.stock.application;

import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.repository.AggregateRepository;
import com.marekturis.stock.domain.Counter;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Transactional
@Named
public class IncreaseCounterCommandHandler implements CommandHandler<IncreaseCounterCommand> {

	private AggregateRepository aggregateRepository;

	public IncreaseCounterCommandHandler(AggregateRepository aggregateRepository) {
		this.aggregateRepository = aggregateRepository;
	}

	@Transactional
	public void handle(IncreaseCounterCommand command) {
		Counter counter = aggregateRepository.getById(command.getId());

		counter.increase(command.getAmmount());
	}
}
