package com.marekturis.stock.infrastructure.readdb.retrievers.dtos;

/**
 * @author Marek Turis
 */
public class SupplierDto {
	private int id;
	private String name;

	public SupplierDto(int id, String name) {
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
