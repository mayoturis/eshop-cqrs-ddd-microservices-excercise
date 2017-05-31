package com.marekturis.common.application.command;

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
		handlers.put(className, commandHandlerBuilder.build(handler));
	}

	public void dispatch(Command command) {
		Class className = command.getClass();
		CommandHandler handler = handlers.get(className);

		if (handler == null) {
			throw new IllegalStateException("Handler for given command doesn't exits");
		}

		handler.handle(command);
	}
}
