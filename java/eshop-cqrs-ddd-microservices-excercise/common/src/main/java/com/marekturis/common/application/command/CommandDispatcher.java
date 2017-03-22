package com.marekturis.common.application.command;

import com.marekturis.common.application.authorization.AuthorizableCommandHandler;
import com.marekturis.common.application.transaction.TransactionalCommandHandler;
import sun.plugin.dom.exception.InvalidStateException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marek Turis
 */
public class CommandDispatcher {

	private Map<Class, CommandHandler> handlers = new HashMap<>();

	private CommandHandlerBuilder commandHandlerBuilder;

	@Inject
	public CommandDispatcher(CommandHandlerBuilder commandHandlerBuilder) {
		this.commandHandlerBuilder = commandHandlerBuilder;
	}

	public void addHandler(Class className, CommandHandler handler) {
		handlers.put(className, handler);
	}

	public void dispatch(Command command) {
		Class className = command.getClass();
		CommandHandler rawHandler = handlers.get(className);

		if (rawHandler == null) {
			throw new InvalidStateException("Handler for given command doesn't exits");
		}

		CommandHandler builtHandler = commandHandlerBuilder.build(rawHandler);
		builtHandler.handle(command);
	}
}
