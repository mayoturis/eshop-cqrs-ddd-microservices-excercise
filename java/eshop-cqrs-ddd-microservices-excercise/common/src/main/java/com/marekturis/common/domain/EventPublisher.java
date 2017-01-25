package com.marekturis.common.domain;

/**
 * @author Marek Turis
 */
public interface EventPublisher {
	void registerHandler(EventHandler handler);
	void publish(Event event);
}
