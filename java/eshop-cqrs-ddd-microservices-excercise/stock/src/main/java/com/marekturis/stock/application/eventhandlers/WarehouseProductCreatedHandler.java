package com.marekturis.stock.application.eventhandlers;

import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.domain.process.ProcessRepository;
import com.marekturis.stock.application.commands.AssociateProductWithWarehouse;
import com.marekturis.stock.application.process.WarehouseProductCreationProcess;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class WarehouseProductCreatedHandler implements EventHandler {

	private ProcessRepository processRepository;
	private CommandDispatcher commandDispatcher;

	public WarehouseProductCreatedHandler(ProcessRepository processRepository, CommandDispatcher commandDispatcher) {
		this.processRepository = processRepository;
		this.commandDispatcher = commandDispatcher;
	}

	@Override
	public String eventToHandle() {
		return "WarehouseProductCreated";
	}

	@Override
	public void handle(ParsableEvent event) {
		WarehouseProductCreationProcess process = (WarehouseProductCreationProcess)
				processRepository.getProcessById(event.getString("processId"));

		if (process == null) {
			// can be caused by multi deliveries
			return;
		}

		commandDispatcher.dispatch(new AssociateProductWithWarehouse(
				process.getWarehouseId(), event.getInt("productId")));

		processRepository.removeProcess(process);
	}
}
