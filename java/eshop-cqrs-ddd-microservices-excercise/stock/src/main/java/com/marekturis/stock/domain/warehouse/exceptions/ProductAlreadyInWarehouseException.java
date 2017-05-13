package com.marekturis.stock.domain.warehouse.exceptions;

import com.marekturis.common.application.validation.ValidationException;

/**
 * @author Marek Turis
 */
public class ProductAlreadyInWarehouseException extends ValidationException {

	public ProductAlreadyInWarehouseException(String message) {
		super(message);
	}

	public ProductAlreadyInWarehouseException(String message, Throwable cause) {
		super(message, cause);
	}
}
