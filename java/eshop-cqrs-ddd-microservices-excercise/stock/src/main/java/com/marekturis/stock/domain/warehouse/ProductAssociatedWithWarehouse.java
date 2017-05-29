package com.marekturis.stock.domain.warehouse;

import com.marekturis.common.domain.event.AggregateEventBase;

/**
 * @author Marek Turis
 */
public class ProductAssociatedWithWarehouse extends AggregateEventBase {

	private int warehouseId;
	private int productId;

	public ProductAssociatedWithWarehouse(int warehouseId, int productId) {
		this.warehouseId = warehouseId;
		this.productId = productId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public int getProductId() {
		return productId;
	}

}
