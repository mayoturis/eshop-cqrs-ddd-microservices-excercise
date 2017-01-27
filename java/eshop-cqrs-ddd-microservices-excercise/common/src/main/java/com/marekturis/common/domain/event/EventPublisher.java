package com.marekturis.common.domain.event;

/**
 * @author Marek Turis
 */
public interface EventPublisher extends AutoCloseable {
	void registerHandler(EventHandler handler);
	void publish(Event event);
}
