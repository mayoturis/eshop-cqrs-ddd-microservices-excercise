package com.marekturis.common.infrastructure.persistance;

/**
 * @author Marek Turis
 */
public class PersistanceException extends RuntimeException {

	public PersistanceException() {
	}

	public PersistanceException(String message) {
		super(message);
	}

	public PersistanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistanceException(Throwable cause) {
		super(cause);
	}
}
