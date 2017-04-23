package com.marekturis.stock.domain.warehouse;

import com.marekturis.common.domain.event.EventBase;

/**
 * @author Marek Turis
 */
public class WarehouseCreated extends EventBase {

	private int id;
	private String location;

	public WarehouseCreated(int id, String location) {
		this.id = id;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

}
