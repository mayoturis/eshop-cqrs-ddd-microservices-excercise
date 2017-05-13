package com.marekturis.stock.infrastructure.readdb.retrievers.dtos;

/**
 * @author Marek Turis
 */
public class ProductSuppliedBySupplierDto {
	private int productId;
	private int supplierId;
	private double price;

	public ProductSuppliedBySupplierDto(int productId, int supplierId, double price) {
		this.productId = productId;
		this.supplierId = supplierId;
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public double getPrice() {
		return price;
	}
}
