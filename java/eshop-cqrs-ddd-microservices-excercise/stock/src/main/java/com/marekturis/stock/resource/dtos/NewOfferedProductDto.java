package com.marekturis.stock.resource.dtos;

import java.math.BigDecimal;

/**
 * @author Marek Turis
 */
public class NewOfferedProductDto {
	private int productId;
	private BigDecimal price;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
