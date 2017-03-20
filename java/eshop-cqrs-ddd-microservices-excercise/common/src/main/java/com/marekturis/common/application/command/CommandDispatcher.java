package com.marekturis.common.application.command;

import com.marekturis.common.application.transaction.TransactionUnit;
import com.marekturis.common.application.transaction.Transactional;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marek Turis
 */
public class CommandDispatcher {

	private Map<Class, CommandHandler> handlers = new HashMap<>();

	private TransactionUnit transactionUnit;

	public CommandDispatcher(TransactionUnit transactionUnit) {
		this.transactionUnit = transactionUnit;
	}

	public void addHandler(Class className, CommandHandler handler) {
		handlers.put(className, handler);
	}

	public void dispatch(Command command) {
		Class className = command.getClass();
		CommandHandler handler = handlers.get(className);
		handle(handler, command);
	}

	private void handle(CommandHandler handler, Command command) {
		// todo shift this to chain of responsibility pattern
		if (!isTransactional(handler)) {
			handler.handle(command);
			return;
		}

		transactionUnit.init();
		handler.handle(command);
		transactionUnit.commit();
	}

	private boolean isTransactional(CommandHandler handler) {
		return handler.getClass().isAnnotationPresent(Transactional.class);
	}
}
