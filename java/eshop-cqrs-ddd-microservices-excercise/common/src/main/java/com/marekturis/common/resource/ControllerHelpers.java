package com.marekturis.common.resource;

import com.marekturis.common.application.authorization.AuthenticationException;

/**
 * @author Marek Turis
 */
public class ControllerHelpers {

	public static String tokenFromAuthorizationHeader(String authorizationHeader) {
		if (authorizationHeader == null || authorizationHeader == "") {
			throw new AuthenticationException("No authorization header was provided");
		}

		String[] parts = authorizationHeader.split(" ");

		if (parts.length != 2) {
			throw new AuthenticationException("Authorization header is not correctly formed");
		}

		return parts[1];
	}
}
