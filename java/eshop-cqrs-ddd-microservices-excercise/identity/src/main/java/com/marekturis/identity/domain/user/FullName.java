package com.marekturis.identity.domain.user;

/**
 * @author Marek Turis
 */
public class FullName {
	private String firstName;
	private String lastName;

	public FullName(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public String getFirstName() {
		return firstName;
	}

	private void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("First name cannot be null");
		}

		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	private void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Last name cannot be null");
		}

		this.lastName = lastName;
	}
}
