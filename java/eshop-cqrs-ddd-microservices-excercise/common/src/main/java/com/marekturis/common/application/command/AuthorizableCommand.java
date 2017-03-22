package com.marekturis.common.application.command;

import com.marekturis.common.application.authorization.Authorizable;

/**
 * @author Marek Turis
 */
public class AuthorizableCommand implements Command, Authorizable {

	private String executorToken;

	public AuthorizableCommand(String executorToken) {
		this.executorToken = executorToken;
	}

	public AuthorizableCommand() {}

	@Override
	public String executorToken() {
		return executorToken;
	}

	public void setExecutorToken(String executorToken) {
		if (this.executorToken != null) {
			throw new IllegalStateException("Executor token is only possible to set, not to change");
		}

		this.executorToken = executorToken;
	}
}
