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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FullName)) return false;

		FullName fullName = (FullName) o;

		if (getFirstName() != null ? !getFirstName().equals(fullName.getFirstName()) : fullName.getFirstName() != null)
			return false;
		return getLastName() != null ? getLastName().equals(fullName.getLastName()) : fullName.getLastName() == null;

	}

	@Override
	public int hashCode() {
		int result = getFirstName() != null ? getFirstName().hashCode() : 0;
		result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
		return result;
	}

	private void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Last name cannot be null");
		}

		this.lastName = lastName;


	}
}
