package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.event.EventBase;

/**
 * @author Marek Turis
 */
public class SupplierCreated extends EventBase {
	private int id;
	private String name;

	public SupplierCreated(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
