package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;

/**
 * @author Marek Turis
 */
public class CreateSupplier extends AuthorizableCommand {

	private String name;

	public CreateSupplier(String executorToken, String name) {
		super(executorToken);
		this.name = name;
	}

	public String name() {
		return name;
	}
}
