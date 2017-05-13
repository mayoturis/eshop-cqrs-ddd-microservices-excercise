package com.marekturis.stock.domain.supplier.exceptions;

import com.marekturis.common.application.validation.ValidationException;

/**
 * @author Marek Turis
 */
public class ProductNotOfferedBySupplierException extends ValidationException {

	public ProductNotOfferedBySupplierException(String message) {
		super(message);
	}

	public ProductNotOfferedBySupplierException(String message, Throwable cause) {
		super(message, cause);
	}
}
