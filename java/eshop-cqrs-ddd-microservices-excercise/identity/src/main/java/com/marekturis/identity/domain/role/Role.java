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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;

		Role role = (Role) o;

		return getRoleName() != null ? getRoleName().equals(role.getRoleName()) : role.getRoleName() == null;

	}

	@Override
	public int hashCode() {
		return getRoleName() != null ? getRoleName().hashCode() : 0;
	}

	public boolean canBeSubstitutedBy(Role role) {
		return RoleType.getSubtitutesForRole(role.getRoleName()).contains(this.getRoleName());
	}
}
