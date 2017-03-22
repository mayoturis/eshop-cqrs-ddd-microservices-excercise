package com.marekturis.stock.domain.warehouse;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.common.domain.event.AggregateEventBase;

/**
 * @author Marek Turis
 */
public class ProductAmmountInWarehouseIncreased extends AggregateEventBase {

	private int warehouseId;
	private int productId;
	private int ammount;

	public ProductAmmountInWarehouseIncreased(int warehouseId, int productId, int ammount) {
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.ammount = ammount;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public int getProductId() {
		return productId;
	}

	public int getAmmount() {
		return ammount;
	}
}
