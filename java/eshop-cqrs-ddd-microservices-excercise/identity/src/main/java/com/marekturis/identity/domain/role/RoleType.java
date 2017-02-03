package com.marekturis.identity.domain.role;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class RoleType {
	public static final String REGISTERED = "registered";
	public static final String SALESMAN = "salesman";
	public static final String ADMIN = "admin";

	public static List<String> getRolesWithSameOrHigherDegree(String roleName) {
		List<String> roles = new ArrayList<String>();
		roles.add(ADMIN);
		if (roleName.equals(ADMIN)) {
			return roles;
		}

		roles.add(SALESMAN);
		if (roleName.equals(SALESMAN)) {
			return roles;
		}

		roles.add(REGISTERED);
		if (roleName.equals(REGISTERED)) {
			return roles;
		}

		throw new IllegalArgumentException("No such role " + roleName);
	}

	public static List<String> getRolesWithSameOrLowerDegree(String roleName) {
		List<String> roles = new ArrayList<String>();
		roles.add(REGISTERED);
		if (roleName.equals(REGISTERED)) {
			return roles;
		}

		roles.add(SALESMAN);
		if (roleName.equals(SALESMAN)) {
			return roles;
		}

		roles.add(ADMIN);
		if (roleName.equals(ADMIN)) {
			return roles;
		}

		throw new IllegalArgumentException("No such role " + roleName);
	}
}
