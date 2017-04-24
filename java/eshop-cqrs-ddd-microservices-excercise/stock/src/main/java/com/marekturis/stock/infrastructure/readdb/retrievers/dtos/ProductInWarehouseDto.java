package com.marekturis.stock.infrastructure.readdb.retrievers.dtos;

/**
 * @author Marek Turis
 */
public class ProductInWarehouseDto {

	private int productId;
	private int ammount;

	public ProductInWarehouseDto(int productId, int ammount) {
		this.productId = productId;
		this.ammount = ammount;
	}

	public int getProductId() {
		return productId;
	}

	public int getAmmount() {
		return ammount;
	}
}
