package com.marekturis.identity.resource.seeding;

import com.marekturis.identity.domain.role.Role;
import com.marekturis.identity.domain.user.FullName;
import com.marekturis.identity.domain.user.Person;
import com.marekturis.identity.domain.user.User;
import com.marekturis.identity.infrastructure.persistence.JpaUserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * @author Marek Turis
 */
@Named
@Transactional
public class DataSeeder {

	@Inject
	JpaUserRepository userRepository;

	public void seedData() {
		addUser("user1@user.com", "password", "registered", "User1", "Userovič");
		addUser("user2@user.com", "password", "registered", "User2", "Userovič");
		addUser("user3@user.com", "password", "registered", "User3", "Userovič");
	}

	private void addUser(String email, String password, String role, String firstName, String lastName) {
		User user = createUser(email, password, role, firstName, lastName);
		userRepository.add(user);
	}

	private User createUser(String email, String password, String role, String firstName, String lastName) {
		FullName fullName = new FullName(firstName, lastName);
		Person person = new Person(fullName, email);
		return new User(password, person, new Role(role));
	}
}
