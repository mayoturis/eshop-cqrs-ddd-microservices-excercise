package com.marekturis.stock.domain.supplier;

import java.io.Serializable;

/**
 * @author Marek Turis
 */
public class OfferedProduct implements Serializable {

	private static final long serialVersionUID = 465165879;

	private int productId;
	private double price;

	public OfferedProduct(int productId, double price) {
		this.productId = productId;
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public double getPrice() {
		return price;
	}
}
