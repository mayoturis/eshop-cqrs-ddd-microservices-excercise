package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.Command;

/**
 * @author Marek Turis
 */
public class AssociateProductWithWarehouse implements Command {
	private int warehouseId;
	private int productId;

	public AssociateProductWithWarehouse(int warehouseId, int productId) {
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
