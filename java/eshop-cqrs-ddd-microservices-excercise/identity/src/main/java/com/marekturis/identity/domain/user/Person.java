package com.marekturis.identity.domain.user;

import com.marekturis.common.application.validation.ValidationException;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Marek Turis
 */
@Embeddable
public class Person {
	@Embedded
	private FullName fullName;

	private String email;

	public Person(FullName fullName, String email) {
		this.setFullName(fullName);
		this.setEmail(email);
	}

	protected Person() {
		// required by JPA
	}

	public FullName getFullName() {
		return fullName;
	}

	private void setFullName(FullName fullName) {
		if (fullName == null) {
			throw new ValidationException("Full name cannot be null");
		}

		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new ValidationException("Email cannot be empty");
		}

		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Person)) return false;

		Person person = (Person) o;

		return getEmail().equals(person.getEmail());

	}

	@Override
	public int hashCode() {
		return getEmail().hashCode();
	}
}
