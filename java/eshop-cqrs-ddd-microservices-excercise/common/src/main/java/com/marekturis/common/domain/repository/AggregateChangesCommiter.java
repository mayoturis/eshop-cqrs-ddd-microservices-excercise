package com.marekturis.common.domain.repository;

import com.marekturis.common.domain.aggregate.AggregateRoot;

/**
 * @author Marek Turis
 */
public interface AggregateChangesCommiter {
	void commitChanges(AggregateRoot aggregateRoot);
}
