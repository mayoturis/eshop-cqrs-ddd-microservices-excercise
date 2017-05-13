package com.marekturis.stock.domain.supplier.exceptions;

import com.marekturis.common.application.validation.ValidationException;

/**
 * @author Marek Turis
 */
public class ProductAlreadyOfferedBySupplierException extends ValidationException {

	public ProductAlreadyOfferedBySupplierException(String message) {
		super(message);
	}

	public ProductAlreadyOfferedBySupplierException(String message, Throwable cause) {
		super(message, cause);
	}
}
