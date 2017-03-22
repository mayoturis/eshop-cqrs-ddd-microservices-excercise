package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;
import com.marekturis.common.application.command.Command;

/**
 * @author Marek Turis
 */
public class IncreaseProductAmmountInWarehouse extends AuthorizableCommand {

	private int warehouseId;
	private int productId;
	private int ammount;

	public IncreaseProductAmmountInWarehouse(int warehouseId, int productId, int ammount, String executorToken) {
		super(executorToken);
		this.warehouseId = warehouseId;
		this.productId = productId;
		this.ammount = ammount;
	}

	protected IncreaseProductAmmountInWarehouse() {}

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
