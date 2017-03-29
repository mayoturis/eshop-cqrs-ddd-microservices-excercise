package com.marekturis.stock.domain.supplier;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Marek Turis
 */
public class OfferedProduct implements Serializable {

	private int productId;
	private BigDecimal price;

	public OfferedProduct(int productId, BigDecimal price) {
		this.productId = productId;
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
