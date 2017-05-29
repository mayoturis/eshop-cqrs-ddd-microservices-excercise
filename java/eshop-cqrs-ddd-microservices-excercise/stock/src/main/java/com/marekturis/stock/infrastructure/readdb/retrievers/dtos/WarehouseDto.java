package com.marekturis.stock.infrastructure.readdb.retrievers.dtos;

/**
 * @author Marek Turis
 */
public class WarehouseDto {
	private String location;
	private int warehouseProductId;
	private int id;

	public WarehouseDto(int id, String location, int warehouseProductId) {
		this.location = location;
		this.id = id;
		this.warehouseProductId = warehouseProductId;
	}

	public int getWarehouseProductId() {
		return warehouseProductId;
	}

	public String getLocation() {
		return location;
	}

	public int getId() {
		return id;
	}
}
