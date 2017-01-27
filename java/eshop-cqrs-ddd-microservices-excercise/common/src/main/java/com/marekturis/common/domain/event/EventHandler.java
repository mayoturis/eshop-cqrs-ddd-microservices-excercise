package com.marekturis.common.domain.event;

/**
 * @author Marek Turis
 */
public interface EventHandler {
	String eventToHandle();
	void handle(ParsableEvent event);
}
