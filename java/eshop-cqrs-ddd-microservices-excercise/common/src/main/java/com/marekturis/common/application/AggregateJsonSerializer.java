package com.marekturis.common.application;

import com.marekturis.common.domain.aggregate.AggregateRoot;

/**
 * @author Marek Turis
 */
public interface AggregateJsonSerializer {
	String fromAggregate(AggregateRoot aggregateRoot);
	<TAggregateRoot> TAggregateRoot fromString(String string, Class<TAggregateRoot> type);
}
