package com.marekturis.stock.application.eventhandlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.domain.process.Process;
import com.marekturis.common.domain.process.ProcessRepository;
import com.marekturis.stock.application.process.WarehouseProductCreationProcess;
import com.marekturis.stock.domain.warehouse.WarehouseCreated;
import com.marekturis.stock.domain.warehouse.WarehouseProductCreationRequested;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class RequestWarehouseProductCreationHandler implements EventHandler {

	private ProcessRepository processRepository;
	private EventPublisher eventPublisher;

	public RequestWarehouseProductCreationHandler(ProcessRepository processRepository, EventPublisher eventPublisher) {
		this.processRepository = processRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public String eventToHandle() {
		return WarehouseCreated.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		Process process = new WarehouseProductCreationProcess(event.getInt("id"), event.getString("location"));
		processRepository.addProcess(process);

		eventPublisher.publish(new WarehouseProductCreationRequested(
				process.getId(), event.getInt("id"), event.getString("location")));
	}
}
