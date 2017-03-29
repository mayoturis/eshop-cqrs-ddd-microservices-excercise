package com.marekturis.common.domain.event;

import java.util.Date;
import java.util.List;

/**
 * @author Marek Turis
 */
public interface AggregateEventStore {
	void add(AggregateEvent event);
	void add(List<AggregateEvent> events);
	List<AggregateEvent> getEvents(int aggregateId, int fromAggregateVersion);
	List<AggregateEvent> getEventsNewerThan(Date date);
}
