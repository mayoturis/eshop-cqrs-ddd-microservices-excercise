package com.marekturis.identity.application;

import com.marekturis.identity.domain.role.Role;
import com.marekturis.identity.domain.user.*;

import javax.inject.Inject;
import java.util.UUID;

/**
 * @author Marek Turis
 */
public class UserService {

	private final HashingService hashingService;
	private final UserRepository userRepository;

	@Inject
	public UserService(HashingService hashingService, UserRepository userRepository) {
		this.hashingService = hashingService;
		this.userRepository = userRepository;
	}


	public void addUser(String email, String firstName, String lastName, String password) {
		FullName fullName = new FullName(firstName, lastName);
		Person person = new Person(fullName, email);
		UUID identity = userRepository.nextIdentity();
		Role role = new Role("registered");
		User user = new User(identity, hashingService.createHash(password), person, role);
		userRepository.add(user);
	}

	public void changePassword(Long userId, String newPassword) {

	}
}
