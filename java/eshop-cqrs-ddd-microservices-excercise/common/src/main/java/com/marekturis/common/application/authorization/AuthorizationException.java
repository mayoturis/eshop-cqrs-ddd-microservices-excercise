package com.marekturis.common.application.authorization;

/**
 * @author Marek Turis
 */
public class AuthorizationException extends RuntimeException {

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

	public AuthorizationException() {
	}
}
