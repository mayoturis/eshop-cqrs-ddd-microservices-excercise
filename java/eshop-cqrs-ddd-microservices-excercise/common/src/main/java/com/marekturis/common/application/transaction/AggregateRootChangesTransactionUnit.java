package com.marekturis.common.application.transaction;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.repository.AggregateChangesCommiter;

import javax.inject.Named;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class AggregateRootChangesTransactionUnit implements TransactionUnit {

	private ThreadLocal<List<AggregateRoot>> loadedAggregates = new ThreadLocal<>();

	private AggregateChangesCommiter aggregateChangesCommiter;

	public AggregateRootChangesTransactionUnit(AggregateChangesCommiter aggregateChangesCommiter) {
		this.aggregateChangesCommiter = aggregateChangesCommiter;
	}

	@Override
	public TransactionUnit init() {
		loadedAggregates = new ThreadLocal<>();
		return this;
	}

	public void trackAggregate(AggregateRoot aggregateRoot) {
		loadedAggregates.get().add(aggregateRoot);
	}

	@Override
	public void commit() {
		for (AggregateRoot aggregateRoot : loadedAggregates.get()) {
			aggregateChangesCommiter.commitChanges(aggregateRoot);
		}
	}
}