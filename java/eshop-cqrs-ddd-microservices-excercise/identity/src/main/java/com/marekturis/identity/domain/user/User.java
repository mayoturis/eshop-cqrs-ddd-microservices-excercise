package com.marekturis.identity.domain.user;

import com.marekturis.identity.domain.role.Role;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author Marek Turis
 */
@Entity
public class User {
	@Id
	private UUID id;
	private String password;
	@Embedded
	private Role role;
	@Embedded
	private Person person;

	public User(UUID id, String password, Person person, Role role) {
		this.setId(id);
		this.setPassword(password);
		this.setPerson(person);
		this.setRole(role);
	}

	protected User() {
		// required by JPA
	}

	public void changePassword(String newPassword) {
		this.setPassword(newPassword);
	}


	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	private void setId(UUID id) {
		if (this.id != null) {
			throw new IllegalArgumentException("Id was already set");
		}

		if (id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}

		this.id = id;
	}

	private void setPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Password cannot be null");
		}

		this.password = password;
	}

	private void setRole(Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Role cannot be null");
		}

		this.role = role;
	}

	private void setPerson(Person person) {
		if (person == null) {
			throw new IllegalArgumentException("Person cannot be null");
		}

		this.person = person;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;

		User user = (User) o;

		return person.equals(user.person);

	}

	@Override
	public int hashCode() {
		return person.hashCode();
	}
}
