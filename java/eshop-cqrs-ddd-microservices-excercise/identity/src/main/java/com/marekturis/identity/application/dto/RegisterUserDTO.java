package com.marekturis.identity.application.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Marek Turis
 */
public class RegisterUserDTO {
	@NotNull
	@Size(min = 6)
	private String password;
	@NotNull
	@Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$")
	private String email;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
