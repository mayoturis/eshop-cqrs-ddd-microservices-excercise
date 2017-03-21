package com.marekturis.common.application.command;

import com.marekturis.common.application.authorization.AuthorizableCommandHandler;
import com.marekturis.common.application.authorization.Authorizator;
import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.transaction.TransactionUnit;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.application.transaction.TransactionalCommandHandler;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class CommandHandlerBuilder {

	private TransactionUnit transactionUnit;

	private Authorizator authorizator;

	@Inject
	public CommandHandlerBuilder(TransactionUnit transactionUnit, Authorizator authorizator) {
		this.transactionUnit = transactionUnit;
		this.authorizator = authorizator;
	}


	public CommandHandler build(CommandHandler handler) {
		CommandHandler buildHandler = handler;
		if (isTransactional(handler)) {
			buildHandler = new TransactionalCommandHandler(handler, transactionUnit);
		}

		if (shouldBeAuthorized(handler)) {
			String roleName = getRoleName(handler);
			buildHandler = new AuthorizableCommandHandler(handler, roleName, authorizator);
		}

		return buildHandler;
	}

	private boolean isTransactional(CommandHandler handler) {
		try {
			return handler.getClass().isAnnotationPresent(Transactional.class)
					|| handler.getClass().getMethod("handle", Command.class).isAnnotationPresent(Transactional.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException("CommandHandler doesn't have handle method", e);
		}
	}

	private boolean shouldBeAuthorized(CommandHandler handler) {
		try {
			return handler.getClass().isAnnotationPresent(Authorize.class)
					|| handler.getClass().getMethod("handle", Command.class).isAnnotationPresent(Authorize.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException("CommandHandler doesn't have handle method", e);
		}
	}

	private String getRoleName(CommandHandler handler) {
		try {
			return handler.getClass().getMethod("handle", Command.class).getAnnotation(Authorize.class).value();
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException("CommandHandler doesn't have handle method", e);
		}
	}

}
