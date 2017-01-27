package com.marekturis.common.domain.repository;

import com.marekturis.common.domain.aggregate.AggregateRoot;

/**
 * @author Marek Turis
 */
public interface BasicRepository<TAggregetRoot extends AggregateRoot> {
	TAggregetRoot getById(Integer id);
	void add(TAggregetRoot aggregetRoot);
}
