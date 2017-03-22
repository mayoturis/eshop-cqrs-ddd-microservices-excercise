package com.marekturis.common.domain.aggregate;

/**
 * @author Marek Turis
 */
public class UnapplicableEventException extends RuntimeException {
	public UnapplicableEventException() {
	}

	public UnapplicableEventException(String message) {
		super(message);
	}

	public UnapplicableEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnapplicableEventException(Throwable cause) {
		super(cause);
	}
}
