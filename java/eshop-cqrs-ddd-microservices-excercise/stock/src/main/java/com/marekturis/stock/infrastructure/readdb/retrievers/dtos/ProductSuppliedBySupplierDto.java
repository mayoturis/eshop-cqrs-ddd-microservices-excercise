package com.marekturis.stock.infrastructure.readdb.retrievers.dtos;

/**
 * @author Marek Turis
 */
public class ProductSuppliedBySupplierDto {
	private int id;

	public ProductSuppliedBySupplierDto(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
