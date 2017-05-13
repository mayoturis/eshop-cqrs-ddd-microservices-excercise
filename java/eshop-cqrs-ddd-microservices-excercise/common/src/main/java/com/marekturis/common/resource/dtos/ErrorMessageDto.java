package com.marekturis.common.resource.dtos;

/**
 * @author Marek Turis
 */
public class ErrorMessageDto {

	private String message;

	public ErrorMessageDto(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
