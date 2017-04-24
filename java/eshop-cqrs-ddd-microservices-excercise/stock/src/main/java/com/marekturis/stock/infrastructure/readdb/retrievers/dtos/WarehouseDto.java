package com.marekturis.stock.infrastructure.readdb.retrievers.dtos;

/**
 * @author Marek Turis
 */
public class WarehouseDto {
	private String location;
	private int id;

	public WarehouseDto(int id, String location) {
		this.location = location;
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public int getId() {
		return id;
	}
}
