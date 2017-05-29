package com.marekturis.common.domain.process;

/**
 * @author Marek Turis
 */
public interface Process {
	void refreshNextTimeOut();
	boolean hasTimedOut();
	void increaseRetries();
	boolean hasTotallyTimedOut();
	String processTimedOutEventType();
	String getId();
}
