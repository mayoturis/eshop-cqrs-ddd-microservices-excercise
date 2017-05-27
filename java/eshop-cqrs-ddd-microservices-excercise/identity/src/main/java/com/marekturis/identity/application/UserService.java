package com.marekturis.identity.application;

import com.marekturis.common.application.authorization.AuthenticationException;
import com.marekturis.identity.application.dto.AuthenticateUserDTO;
import com.marekturis.identity.application.dto.RegisterUserDTO;
import com.marekturis.identity.application.dto.UserDTO;
import com.marekturis.identity.domain.role.Role;
import com.marekturis.identity.domain.role.RoleType;
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
		Role role = new Role(RoleType.REGISTERED);
		User user = new User(hashingService.createHash(registerUserDTO.getPassword()), person, role);
		userRepository.add(user);
	}

	public String authenticate(AuthenticateUserDTO authenticateUserDTO) {
		return this.authenticationService.authenticateUser(authenticateUserDTO.getEmail(), authenticateUserDTO.getPassword());
	}

	public UserDTO getUserByAuthenticationToken(String authenticationToken) {
		return map(getUser(authenticationToken));
	}

	public UserDTO getUserInRole(String authenticationToken, String roleName) {
		User user = getUser(authenticationToken);
		if (user == null) {
			return null;
		}

		if (!user.hasRole(new Role(roleName))) {
			return null;
		}

		return map(user);
	}

	private User getUser(String authenticationToken) {
		User user = userRepository.getByAuthenticationToken(authenticationToken);

		if (user == null) {
			return null;
		}

		if (user.tokenExpired()) {
			return null;
		}

		user.refreshAuthenticationToken();
		return user;
	}

	private UserDTO map(User user) {
		if (user == null) {
			return null;
		}

		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getPerson().getEmail());
		userDTO.setFirstName(user.getPerson().getFullName().getFirstName());
		userDTO.setLastName(user.getPerson().getFullName().getLastName());
		userDTO.setRoles(user.getRole().allSuperRoles());
		return userDTO;
	}

	public boolean tokenBelongsToUser(String authenticationToken, Integer userId) {
		User user = userRepository.getByAuthenticationToken(authenticationToken);

		if (user == null) {
			return false;
		}

		if (user.tokenExpired()) {
			return false;
		}

		return user.getId().equals(userId);
	}
}
