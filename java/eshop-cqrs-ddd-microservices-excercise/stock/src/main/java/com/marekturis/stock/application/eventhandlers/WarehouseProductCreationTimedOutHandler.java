package com.marekturis.stock.application.eventhandlers;

import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.stock.application.process.WarehouseProductCreationProcessTimedOutEvent;
import com.marekturis.stock.domain.warehouse.WarehouseProductCreationRequested;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class WarehouseProductCreationTimedOutHandler implements EventHandler {

	private EventPublisher eventPublisher;

	public WarehouseProductCreationTimedOutHandler(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public String eventToHandle() {
		return WarehouseProductCreationProcessTimedOutEvent.class.getSimpleName();
	}

	@Override
	public void handle(ParsableEvent event) {
		if (event.getBoolean("totallyTimedOut")) {
			notifyAdmin();
			return;
		}

		eventPublisher.publish(new WarehouseProductCreationRequested(
				event.getString("processId"),
				event.getInt("warehouseId"),
				event.getString("location")));
	}

	private void notifyAdmin() {
		// not implemented
	}
}
