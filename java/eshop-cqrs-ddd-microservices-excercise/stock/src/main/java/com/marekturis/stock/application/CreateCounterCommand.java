package com.marekturis.stock.application;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandHandler;

/**
 * @author Marek Turis
 */
public class CreateCounterCommand implements Command {

	private int id;

	public CreateCounterCommand(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
