package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.event.AggregateEventBase;

import java.math.BigDecimal;

/**
 * @author Marek Turis
 */
public class NewProductOfferedBySupplier extends AggregateEventBase {

	private int supplierId;
	private int productId;
	private BigDecimal price;

	public NewProductOfferedBySupplier(int supplierId, int productId, BigDecimal price) {
		this.supplierId = supplierId;
		this.productId = productId;
		this.price = price;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public int getProductId() {
		return productId;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
