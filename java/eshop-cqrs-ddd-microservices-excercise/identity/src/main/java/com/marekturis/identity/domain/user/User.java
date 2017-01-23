package com.marekturis.identity.domain.user;

import com.marekturis.identity.domain.role.Role;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Marek Turis
 */
@Entity
public class User {
	public static final int TOKEN_EXPIRATION_IN_MINUTES = 30;
	@Id
	@GeneratedValue
	private Integer id;

	private String password;
	@Embedded
	private Role role;
	@Embedded
	private Person person;

	private String authenticationToken;

	private Date authenticationTokenExpirationDate;

	public User(String password, Person person, Role role) {
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

	public void setAuthenticationToken(String token) {
		this.authenticationToken = token;
		this.authenticationTokenExpirationDate = determineTokenExpiration();
	}

	public void refreshAuthenticationToken() {
		if (tokenExpired()) {
			throw new IllegalStateException("Token already expired");
		}

		this.authenticationTokenExpirationDate = determineTokenExpiration();
	}

	public boolean tokenExpired() {
		Date currentDate = new Date();
		return currentDate.after(authenticationTokenExpirationDate);
	}

	private Date determineTokenExpiration() {
		Calendar currentDate = Calendar.getInstance();
		long currentDateTimeInMillis = currentDate.getTimeInMillis();
		return new Date(currentDateTimeInMillis + (TOKEN_EXPIRATION_IN_MINUTES * 60000));
	}

	public void setId(Integer id) {
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

	public Person getPerson() {
		return person;
	}
}
