package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;

/**
 * @author Marek Turis
 */
public class RemoveOfferedProductFromSupplier extends AuthorizableCommand {

	private int supplierId;
	private int productId;

	public RemoveOfferedProductFromSupplier(String executorToken, int supplierId, int productId) {
		super(executorToken);
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
