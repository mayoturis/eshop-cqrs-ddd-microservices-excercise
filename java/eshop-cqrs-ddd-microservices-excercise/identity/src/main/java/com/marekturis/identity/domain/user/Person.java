package com.marekturis.identity.domain.user;

/**
 * @author Marek Turis
 */
public class Person {
	private FullName fullName;
	private String email;

	public Person(FullName fullName, String email) {
		this.setFullName(fullName);
		this.setEmail(email);
	}

	private Person() {
		// required by ORM
	}

	public FullName getFullName() {
		return fullName;
	}

	private void setFullName(FullName fullName) {
		if (fullName == null) {
			throw new IllegalArgumentException("Full name cannot be null");
		}

		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("Email cannot be null");
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
