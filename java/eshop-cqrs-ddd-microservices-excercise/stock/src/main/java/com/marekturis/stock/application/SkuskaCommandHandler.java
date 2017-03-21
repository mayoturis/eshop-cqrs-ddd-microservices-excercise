package com.marekturis.stock.application;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.domain.RoleTypes;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class SkuskaCommandHandler implements CommandHandler<SkuskaCommand> {

	@Authorize(RoleTypes.ADMIN)
	public void handle(SkuskaCommand command) {
		System.out.println("Command presiel");
	}
}
