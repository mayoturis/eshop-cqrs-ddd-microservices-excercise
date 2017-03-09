package com.marekturis.common.application.transaction;

/**
 * @author Marek Turis
 */
public interface TransactionUnit {
	TransactionUnit init();
	void commit();
}
