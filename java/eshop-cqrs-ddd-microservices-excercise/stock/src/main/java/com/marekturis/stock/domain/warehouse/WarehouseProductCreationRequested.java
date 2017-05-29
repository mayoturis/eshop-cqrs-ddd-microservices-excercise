package com.marekturis.stock.domain.warehouse;

import com.marekturis.common.domain.event.EventBase;

/**
 * @author Marek Turis
 */
public class WarehouseProductCreationRequested extends EventBase {

	private String processId;
	private int warehouseId;
	private String location;

	public WarehouseProductCreationRequested(String processId, int warehouseId, String location) {
		this.processId = processId;
		this.warehouseId = warehouseId;
		this.location = location;
	}

	public String getProcessId() {
		return processId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public String getLocation() {
		return location;
	}
}
