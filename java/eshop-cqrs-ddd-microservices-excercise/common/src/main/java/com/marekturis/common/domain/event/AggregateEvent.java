package com.marekturis.common.domain.event;

/**
 * @author Marek Turis
 */
public interface AggregateEvent extends Event {
	int aggregateIdentity();
	int aggregateVersion();
	void setAggregateIdentity(Integer id);
	void setAggregateVersion(int version);
}
