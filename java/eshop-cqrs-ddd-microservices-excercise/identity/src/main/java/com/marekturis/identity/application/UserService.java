package com.marekturis.identity.application;

import com.marekturis.identity.application.dto.AuthenticateUserDTO;
import com.marekturis.identity.application.dto.RegisterUserDTO;
import com.marekturis.identity.application.dto.UserDTO;
import com.marekturis.identity.domain.role.Role;
import com.marekturis.identity.domain.user.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * @author Marek Turis
 */
@Named
@Transactional
public class UserService {

	private final HashingService hashingService;
	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;

	@Inject
	public UserService(HashingService hashingService,
					   UserRepository userRepository,
					   AuthenticationService authenticationService) {
		this.hashingService = hashingService;
		this.userRepository = userRepository;
		this.authenticationService = authenticationService;
	}


	public void addUser(RegisterUserDTO registerUserDTO) {
		FullName fullName = new FullName(registerUserDTO.getFirstName(), registerUserDTO.getLastName());
		Person person = new Person(fullName, registerUserDTO.getEmail());
		Role role = new Role("registered");
		User user = new User(hashingService.createHash(registerUserDTO.getPassword()), person, role);
		userRepository.add(user);
	}

	public String authenticate(AuthenticateUserDTO authenticateUserDTO) {
		return this.authenticationService.authenticateUser(authenticateUserDTO.getEmail(), authenticateUserDTO.getPassword());
	}

	public void changePassword(Long userId, String newPassword) {

	}

	public UserDTO getUserByAuthenticationToken(String authenticationToken) {
		User user = userRepository.getByAuthenticationToken(authenticationToken);

		if (user == null) {
			return null;
		}

		if (user.tokenExpired()) {
			return null;
		}

		user.refreshAuthenticationToken();

		return map(user);
	}

	private UserDTO map(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword(user.getPassword());
		userDTO.setEmail(user.getPerson().getEmail());
		userDTO.setFirstName(user.getPerson().getFullName().getFirstName());
		userDTO.setLastName(user.getPerson().getFullName().getLastName());
		return userDTO;
	}
}
