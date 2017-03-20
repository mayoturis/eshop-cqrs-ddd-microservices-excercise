package com.marekturis.stock.application;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.repository.AggregateRepository;
import com.marekturis.stock.domain.Counter;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
@Transactional
public class CreateCounterCommandHandler implements CommandHandler<CreateCounterCommand> {

	private AggregateRepository aggregateRepository;

	public CreateCounterCommandHandler(AggregateRepository aggregateRepository) {
		this.aggregateRepository = aggregateRepository;
	}

	@Transactional
	public void handle(CreateCounterCommand command) {
		Counter counter = new Counter(command.getId());

		aggregateRepository.add(counter);
	}
}
