package com.marekturis.identity.infrastructure.persistence;

import com.marekturis.identity.domain.user.User;
import com.marekturis.identity.domain.user.UserRepository;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void add(User user) {
		entityManager.persist(user);
	}

	public User getById(Integer id) {
		return getOneByField("id", id);
	}

	public User getByEmail(String email) {
		return getOneByField("email", email);
	}

	public User getByAuthenticationToken(String authenticationToken) {
		return getOneByField("authenticationToken", authenticationToken);
	}

	private User getOneByField(String fieldName, Object fieldValue) {
		List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE " + fieldName +
				" = :" + fieldName, User.class)
				.setParameter(fieldName, fieldValue)
				.getResultList();

		if (users.size() == 0) {
			return null;
		}

		return users.get(0);
	}
}
