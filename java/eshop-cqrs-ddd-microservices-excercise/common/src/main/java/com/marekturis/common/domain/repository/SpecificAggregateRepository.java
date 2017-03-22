package com.marekturis.common.domain.repository;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.aggregate.AggregateRootBase;

/**
 * @author Marek Turis
 */
public interface SpecificAggregateRepository<TAggregateRoot extends AggregateRoot> {
	TAggregateRoot getById(Integer id);
	void add(TAggregateRoot aggregetRoot);
}
