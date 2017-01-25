package com.marekturis.identity.application.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Marek Turis
 */
public class AuthenticateUserDTO {
	@NotNull
	private String email;
	@NotNull
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
