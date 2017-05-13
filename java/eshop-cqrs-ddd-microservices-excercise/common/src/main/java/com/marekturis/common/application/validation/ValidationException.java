package com.marekturis.common.application.validation;

/**
 * @author Marek Turis
 */
public class ValidationException extends RuntimeException {

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getValidationErrorMessage() {
		return getMessage();
	}
}
