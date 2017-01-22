package com.marekturis.identity.domain.role;

import javax.persistence.Embeddable;

/**
 * @author Marek Turis
 */
@Embeddable
public class Role {
	private String roleName;

	public Role(String roleName) {
		this.setRoleName(roleName);
	}

	protected Role() {
		// Required by JPA
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
