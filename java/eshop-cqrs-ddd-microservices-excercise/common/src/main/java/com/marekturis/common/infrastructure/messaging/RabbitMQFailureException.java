package com.marekturis.common.infrastructure.messaging;

/**
 * @author Marek Turis
 */
public class RabbitMQFailureException extends RuntimeException {

	public RabbitMQFailureException() {
	}

	public RabbitMQFailureException(String message) {
		super(message);
	}

	public RabbitMQFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public RabbitMQFailureException(Throwable cause) {
		super(cause);
	}
}
