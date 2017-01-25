package com.marekturis.common.domain;

/**
 * @author Marek Turis
 */
public interface EventHandler {
	String eventToHandle();
	void handle(ParsableEvent event);
}
