package com.marekturis.common.infrastructure.persistance;

/**
 * @author Marek Turis
 */
public class OptimisticLockException extends RuntimeException {
	public OptimisticLockException() {
	}

	public OptimisticLockException(String message) {
		super(message);
	}

	public OptimisticLockException(String message, Throwable cause) {
		super(message, cause);
	}

	public OptimisticLockException(Throwable cause) {
		super(cause);
	}
}
