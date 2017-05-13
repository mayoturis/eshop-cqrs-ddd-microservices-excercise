package com.marekturis.common.application.validation;

/**
 * @author Marek Turis
 */
public class BasicValidator {

	public void notNull(Object obj, String propertyName) {
		if (obj == null) {
			throw new ValidationException(propertyName + " cannot be null");
		}
	}

	public void notNull(Object obj) {
		notNull(obj, "Given property");
	}

	public void notEmpty(String str, String propertyName) {
		notNull(str, propertyName);
		if (str.equals("")) {
			throw new ValidationException(propertyName + " cannot be empty");
		}
	}

	public void notEmpty(String str) {
		notEmpty(str, "Given property");
	}

	public void moreThanZero(int number, String propertyName) {
		if (number <= 0) {
			throw new ValidationException(propertyName + " has to be larger than zero");
		}
	}

	public void moreThanZero(int number) {
		moreThanZero(number, "Given property");
	}

	public void moreThanZero(double number, String propertyName) {
		if (number <= 0) {
			throw new ValidationException(propertyName + " has to be larger than zero");
		}
	}

	public void moreThanZero(double number) {
		moreThanZero(number, "Given property");
	}
}
