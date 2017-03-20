package com.marekturis.common.application.command;

/**
 * @author Marek Turis
 */
public interface CommandHandler<T extends Command> {
	void handle(T command);
}
