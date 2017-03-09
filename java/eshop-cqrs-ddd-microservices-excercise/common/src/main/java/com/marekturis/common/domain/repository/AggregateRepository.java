package com.marekturis.common.domain.repository;

import com.marekturis.common.domain.aggregate.AggregateRoot;

/**
 * @author Marek Turis
 */
public interface AggregateRepository {
	<TAggregateRoot extends AggregateRoot> TAggregateRoot getById(Integer id);
	void add(AggregateRoot aggregetRoot);
}
