package com.marekturis.stock.resource.dtos;

/**
 * @author Marek Turis
 */
public class NewOfferedProductDto {
	private int productId;
	private double price;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
