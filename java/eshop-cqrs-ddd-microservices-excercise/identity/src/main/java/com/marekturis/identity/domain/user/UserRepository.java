package com.marekturis.identity.domain.user;

import java.util.UUID;

/**
 * @author Marek Turis
 */
public interface UserRepository {
	void add(User user);
	User getByEmail(String email);
	UUID nextIdentity();
}
