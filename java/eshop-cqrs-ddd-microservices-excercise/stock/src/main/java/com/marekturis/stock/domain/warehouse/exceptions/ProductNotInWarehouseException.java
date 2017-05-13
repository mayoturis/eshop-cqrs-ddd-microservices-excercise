package com.marekturis.stock.domain.warehouse.exceptions;

import com.marekturis.common.application.validation.ValidationException;

/**
 * @author Marek Turis
 */
public class ProductNotInWarehouseException extends ValidationException {

	public ProductNotInWarehouseException(String message) {
		super(message);
	}

	public ProductNotInWarehouseException(String message, Throwable cause) {
		super(message, cause);
	}
}
