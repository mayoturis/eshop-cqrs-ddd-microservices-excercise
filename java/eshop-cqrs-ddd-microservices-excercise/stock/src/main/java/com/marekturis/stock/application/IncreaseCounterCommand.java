package com.marekturis.stock.application;

import com.marekturis.common.application.command.Command;

/**
 * @author Marek Turis
 */
public class IncreaseCounterCommand implements Command {
	private int ammount;
	private int id;

	public IncreaseCounterCommand(int ammount, int id) {
		this.ammount = ammount;
		this.id = id;
	}

	public int getAmmount() {
		return ammount;
	}

	public int getId() {
		return id;
	}
}
