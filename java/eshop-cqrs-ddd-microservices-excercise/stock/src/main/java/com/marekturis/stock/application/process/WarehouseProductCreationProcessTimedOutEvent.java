package com.marekturis.stock.application.process;

import com.marekturis.common.domain.process.AbstractProcessTimedOutEvent;

/**
 * @author Marek Turis
 */
public class WarehouseProductCreationProcessTimedOutEvent extends AbstractProcessTimedOutEvent {

	String processId;
	int warehouseId;
	String location;

	public WarehouseProductCreationProcessTimedOutEvent(WarehouseProductCreationProcess process, boolean totallyTimedOut) {
		super(totallyTimedOut);
		processId = process.getId();
		warehouseId = process.getWarehouseId();
		location = process.getWarehouseLocation();
	}
}
