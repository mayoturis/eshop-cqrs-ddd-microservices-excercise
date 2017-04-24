package com.marekturis.identity.application;

import com.marekturis.common.application.authorization.AuthorizationException;
import com.marekturis.common.application.validation.NotFoundException;
import com.marekturis.identity.application.dto.ChangeUserRoleDTO;
import com.marekturis.identity.domain.role.Role;
import com.marekturis.identity.domain.role.RoleType;
import com.marekturis.identity.domain.user.User;
import com.marekturis.identity.domain.user.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * @author Marek Turis
 */
@Named
@Transactional
public class RoleService {

	private UserRepository userRepository;

	@Inject
	public RoleService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void changeUserRole(ChangeUserRoleDTO userRoleDTO) {
		// todo this could be done more generically
		authorizeOperation(userRoleDTO.getExecutorToken(), RoleType.ADMIN);

		User user = userRepository.getById(userRoleDTO.getUserId());

		if (user == null) {
			throw new NotFoundException("User was not found");
		}

		user.changeRole(new Role(userRoleDTO.getNewRole()));
	}

	private void authorizeOperation(String executorToken, String allowedRole) {
		User user = userRepository.getByAuthenticationToken(executorToken);
		if (user == null) {
			throw new AuthorizationException("User is not logged in");
		}

		if (!user.getRole().equals(new Role(allowedRole))) {
			throw new AuthorizationException("User is not authorized to perform given action");
		}
	}
}
