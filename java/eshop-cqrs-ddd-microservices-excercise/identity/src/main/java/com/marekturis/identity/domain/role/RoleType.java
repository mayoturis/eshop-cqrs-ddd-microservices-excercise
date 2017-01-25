package com.marekturis.identity.domain.role;

/**
 * @author Marek Turis
 */
public class RoleType {
	public static final String REGISTERED = "registered";
	public static final String SALESMAN = "salesman";
	public static final String ADMIN = "admin";

	public static boolean existsRole(String roleName) {
		return roleName == REGISTERED ||
				roleName == SALESMAN ||
				roleName == ADMIN;
	}
}
