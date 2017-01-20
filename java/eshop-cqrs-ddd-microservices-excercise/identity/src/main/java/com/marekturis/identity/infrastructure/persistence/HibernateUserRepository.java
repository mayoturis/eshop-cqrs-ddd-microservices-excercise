package com.marekturis.identity.infrastructure.persistence;

import com.marekturis.identity.domain.user.User;
import com.marekturis.identity.domain.user.UserRepository;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

/**
 * @author Marek Turis
 */
@Named
public class HibernateUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void add(User user) {
		entityManager.persist(user);
	}

	public User getByEmail(String email) {
		List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE email = :email", User.class)
				.setParameter("email", email)
				.getResultList();

		if (users.size() == 0) {
			return null;
		}

		return users.get(0);
	}

	public UUID nextIdentity() {
		return java.util.UUID.randomUUID();
	}
}
