package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;
import com.marekturis.common.application.command.Command;

/**
 * @author Marek Turis
 */
public class AddNewProductToWarehouse extends AuthorizableCommand {

	private int warehouseId;
	private int productId;

	public AddNewProductToWarehouse(int warehouseId, int productId, String executorToken) {
		super(executorToken);
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
