package com.marekturis.identity.domain.user;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Marek Turis
 */
@Named
public class AuthenticationService {

	private UserRepository userRepository;
	private HashingService hashingService;

	private static final int TOKEN_DURATION_IN_MINUTES = 30;
	private static final long ONE_MINUTE_IN_MILLIS = 60000;

	@Inject
	public AuthenticationService(UserRepository userRepository, HashingService hashingService) {
		this.userRepository = userRepository;
		this.hashingService = hashingService;
	}

	public String authenticateUser(String email, String password) {
		User user = getAuthenticatedUser(email, password);
		if (user == null) {
			return null;
		}

		String randomToken = generateRandomToken();
		user.setAuthenticationToken(randomToken);

		return randomToken;
	}

	private User getAuthenticatedUser(String email, String password) {
		User user = userRepository.getByEmail(email);
		if (user == null) {
			return null;
		}

		if (!this.hashingService.compareHashWithPlainText(user.getPassword(), password)) {
			return null;
		}
		return user;
	}


	private String generateRandomToken() {
		SecureRandom secureRandom = new SecureRandom();
		return new BigInteger(130, secureRandom).toString(32);
	}
}
