package com.marekturis.common.application.authorization;

/**
 * @author Marek Turis
 */
public class BasicAuthorizable implements Authorizable {

	private String executorToken;

	public BasicAuthorizable(String executorToken) {
		this.executorToken = executorToken;
	}

	@Override
	public String executorToken() {
		return executorToken;
	}
}
