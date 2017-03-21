package com.marekturis.stock.application;

import com.marekturis.common.application.authorization.Authorizable;
import com.marekturis.common.application.command.Command;

/**
 * @author Marek Turis
 */
public class SkuskaCommand implements Command, Authorizable {

	private String executorToken;

	public SkuskaCommand(String executorToken) {
		this.executorToken = executorToken;
	}

	public String executorToken() {
		return executorToken;
	}
}
