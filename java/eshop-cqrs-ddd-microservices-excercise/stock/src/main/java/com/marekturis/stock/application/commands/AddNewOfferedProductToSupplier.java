package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;
import javafx.scene.layout.BackgroundImage;

import java.math.BigDecimal;

/**
 * @author Marek Turis
 */
public class AddNewOfferedProductToSupplier extends AuthorizableCommand {

	private int supplierId;
	private int productId;
	private BigDecimal price;

	public AddNewOfferedProductToSupplier(String executorToken, int supplierId, int productId, BigDecimal price) {
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

	public BigDecimal getPrice() {
		return price;
	}
}
