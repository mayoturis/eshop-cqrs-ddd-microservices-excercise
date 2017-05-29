package com.marekturis.stock.application.process;

import com.marekturis.common.domain.process.AbstractProcess;

import java.util.Date;

/**
 * @author Marek Turis
 */
public class WarehouseProductCreationProcess extends AbstractProcess {

	private static final int RETRIES_ALLOWED = 3;
	private static final int SECONDS_TO_TIME_OUT = 2 * 60;

	private int warehouseId;
	private String warehouseLocation;

	public WarehouseProductCreationProcess(int warehouseId, String warehouseLocation) {
		super(new Date(), RETRIES_ALLOWED, SECONDS_TO_TIME_OUT);
		this.warehouseId = warehouseId;
		this.warehouseLocation = warehouseLocation;
	}

	@Override
	public String processTimedOutEventType() {
		return WarehouseProductCreationProcessTimedOutEvent.class.getName();
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public String getWarehouseLocation() {
		return warehouseLocation;
	}
}
