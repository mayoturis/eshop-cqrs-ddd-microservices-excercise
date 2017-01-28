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

	public static List<String> getSubtitutesForRole(String roleName) {
		List<String> substitutes = new ArrayList<String>();
		substitutes.add(ADMIN);
		if (roleName.equals(ADMIN)) {
			return substitutes;
		}

		substitutes.add(SALESMAN);
		if (roleName.equals(SALESMAN)) {
			return substitutes;
		}

		substitutes.add(REGISTERED);
		if (roleName.equals(REGISTERED)) {
			return substitutes;
		}

		throw new IllegalArgumentException("No such role " + roleName);
	}
}
