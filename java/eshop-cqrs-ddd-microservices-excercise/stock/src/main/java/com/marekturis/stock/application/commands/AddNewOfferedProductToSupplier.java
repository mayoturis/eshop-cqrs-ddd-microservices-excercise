package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;

/**
 * @author Marek Turis
 */
public class AddNewOfferedProductToSupplier extends AuthorizableCommand {

	private int supplierId;
	private int productId;
	private double price;

	public AddNewOfferedProductToSupplier(String executorToken, int supplierId, int productId, double price) {
		super(executorToken);
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
