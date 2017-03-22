package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;

/**
 * @author Marek Turis
 */
public class DecreaseProductAmmountInWarehouse extends AuthorizableCommand {

	private int warehouseId;
	private int productId;
	private int ammount;

	public DecreaseProductAmmountInWarehouse(int warehouseId, int productId, int ammount, String executorToken) {
		super(executorToken);
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
