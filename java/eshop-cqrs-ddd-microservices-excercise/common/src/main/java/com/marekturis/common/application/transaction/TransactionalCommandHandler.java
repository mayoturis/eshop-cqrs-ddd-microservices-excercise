package com.marekturis.common.application.transaction;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandHandler;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
public class TransactionalCommandHandler implements CommandHandler {

	private CommandHandler successor;
	private TransactionUnit transactionUnit;

	public TransactionalCommandHandler(CommandHandler successor, TransactionUnit transactionUnit) {
		this.successor = successor;
		this.transactionUnit = transactionUnit;
	}

	@Override
	public void handle(Command command) {
		transactionUnit.init();
		successor.handle(command);
		transactionUnit.commit();
	}
}
