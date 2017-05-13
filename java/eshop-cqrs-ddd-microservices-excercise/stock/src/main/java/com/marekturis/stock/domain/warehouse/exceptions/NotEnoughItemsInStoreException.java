package com.marekturis.stock.domain.warehouse.exceptions;

import com.marekturis.common.application.validation.ValidationException;

/**
 * @author Marek Turis
 */
public class NotEnoughItemsInStoreException extends ValidationException {

	public NotEnoughItemsInStoreException(String message) {
		super(message);
	}

	public NotEnoughItemsInStoreException(String message, Throwable cause) {
		super(message, cause);
	}
}
