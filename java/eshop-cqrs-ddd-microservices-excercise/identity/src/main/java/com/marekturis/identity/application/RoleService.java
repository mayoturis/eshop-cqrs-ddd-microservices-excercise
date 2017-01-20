package com.marekturis.identity.application;

import com.marekturis.identity.domain.user.UserRepository;

import javax.inject.Inject;

/**
 * @author Marek Turis
 */
public class RoleService {

	private UserRepository userRepository;

	@Inject
	public RoleService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void changeUserRole(Long userId, String newRole) {

	}
}
