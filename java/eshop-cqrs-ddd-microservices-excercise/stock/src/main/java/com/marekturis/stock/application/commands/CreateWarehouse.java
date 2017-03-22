package com.marekturis.stock.application.commands;

import com.marekturis.common.application.command.AuthorizableCommand;

/**
 * @author Marek Turis
 */
public class CreateWarehouse extends AuthorizableCommand {

	private String location;

	public CreateWarehouse(String location, String executorToken) {
		super(executorToken);
		this.location = location;
	}

	public String location() {
		return location;
	}
}
