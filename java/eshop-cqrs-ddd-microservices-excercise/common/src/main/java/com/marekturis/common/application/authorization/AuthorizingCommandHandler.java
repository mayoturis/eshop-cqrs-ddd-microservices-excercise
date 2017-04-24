package com.marekturis.common.application.authorization;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandHandler;

/**
 * @author Marek Turis
 */
public class AuthorizingCommandHandler implements CommandHandler {

	private CommandHandler successor;
	private String roleName;
	private Authorizator authorizator;


	public AuthorizingCommandHandler(CommandHandler successor, String roleName, Authorizator authorizator) {
		this.successor = successor;
		this.roleName = roleName;
		this.authorizator = authorizator;
	}

	@Override
	public void handle(Command command) {
		if (!(command instanceof Authorizable)) {
			throw new IllegalStateException("Command which was supposed to be authorized " +
					"doesn't implement " + Authorizable.class + " interface");
		}

		Authorizable authorizable = (Authorizable) command;

		if (!authorizator.canBeAuthorized(authorizable.executorToken(), roleName)) {
			throw new AuthorizationException("User with given token is not allowed to perform given action");
		}

		successor.handle(command);
	}
}
