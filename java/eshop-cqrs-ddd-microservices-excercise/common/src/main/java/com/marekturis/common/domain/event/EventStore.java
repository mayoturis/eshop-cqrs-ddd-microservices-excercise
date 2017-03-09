package com.marekturis.common.domain.event;

import java.util.List;

/**
 * @author Marek Turis
 */
public interface EventStore {
	void add(Event event, int aggregateId);
	void add(List<Event> events, int aggregateId);
}
