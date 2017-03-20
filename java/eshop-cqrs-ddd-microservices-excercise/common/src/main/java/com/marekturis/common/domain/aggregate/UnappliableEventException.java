package com.marekturis.common.domain.aggregate;

/**
 * @author Marek Turis
 */
public class UnappliableEventException extends RuntimeException {
	public UnappliableEventException() {
	}

	public UnappliableEventException(String message) {
		super(message);
	}

	public UnappliableEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnappliableEventException(Throwable cause) {
		super(cause);
	}
}
