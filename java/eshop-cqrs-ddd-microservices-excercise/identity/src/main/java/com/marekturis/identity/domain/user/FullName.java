package com.marekturis.identity.domain.user;

import javax.persistence.Embeddable;

/**
 * @author Marek Turis
 */
@Embeddable
public class FullName {
	private String firstName;
	private String lastName;

	public FullName(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	protected FullName() {
		// required by JPA
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
