package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.event.AggregateEventBase;

/**
 * @author Marek Turis
 */
public class NewProductOfferedBySupplier extends AggregateEventBase {

	private int supplierId;
	private int productId;
	private double price;

	public NewProductOfferedBySupplier(int supplierId, int productId, double price) {
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

	public double getPrice() {
		return price;
	}
}
