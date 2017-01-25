package com.marekturis.identity.domain.user;

/**
 * @author Marek Turis
 */
public interface UserRepository {
	void add(User user);
	User getById(Integer id);
	User getByEmail(String email);
	User getByAuthenticationToken(String authenticationToken);
}
