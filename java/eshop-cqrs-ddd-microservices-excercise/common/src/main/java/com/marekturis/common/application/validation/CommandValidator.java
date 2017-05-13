package com.marekturis.common.application.validation;

import com.marekturis.common.application.command.Command;

/**
 * @author Marek Turis
 */
public interface CommandValidator<TCommand extends Command> {
	void validate(TCommand command);
}
