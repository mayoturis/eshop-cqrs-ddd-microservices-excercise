package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.event.AggregateEventBase;


/**
 * @author Marek Turis
 */
public class OfferedProductRemovedFromSupplier extends AggregateEventBase {

	private int supplierId;
	private int productId;

	public OfferedProductRemovedFromSupplier(int supplierId, int productId) {
		this.supplierId = supplierId;
		this.productId = productId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public int getProductId() {
		return productId;
	}

}
