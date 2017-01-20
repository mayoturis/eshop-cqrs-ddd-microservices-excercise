package com.marekturis.identity.domain.role;

/**
 * @author Marek Turis
 */
public class Role {
	private String roleName;

	public Role(String roleName) {
		this.setRoleName(roleName);
	}

	private Role() {
		// Required by ORM
	}

	public String getRoleName() {
		return roleName;
	}

	private void setRoleName(String roleName) {
		if (roleName == null) {
			throw new IllegalArgumentException("Role name cannot be null");
		}

		this.roleName = roleName;
	}
}
