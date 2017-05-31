package com.marekturis.common.application.authorization;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Aspect
@Named
public class AuthorizingAspect {

	private Authorizator authorizator;

	public AuthorizingAspect(Authorizator authorizator) {
		this.authorizator = authorizator;
	}

	@Before("execution(@com.marekturis.common.application.authorization.Authorize * *(..)) && @annotation(authorize)")
	public void authorize(JoinPoint jointPoint, Authorize authorize) throws Throwable {
		String role = authorize.value();
		String executorToken = getExecutorToken(jointPoint);

		if (!authorizator.canBeAuthorized(executorToken, role)) {
			throw new AuthorizationException("User with given token is not allowed to perform given action");
		}
	}

	private String getExecutorToken(JoinPoint jointPoint) {
		Object[] arguments = jointPoint.getArgs();
		for (Object argument : arguments) {
			if (argument instanceof Authorizable) {
				Authorizable authorizable = (Authorizable) argument;
				return authorizable.executorToken();
			}
		}

		throw new IllegalStateException("None of the parameters of the method which should be " +
				"authorized implements " + Authorizable.class);
	}
}
