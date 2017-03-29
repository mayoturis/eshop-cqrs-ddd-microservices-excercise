package com.marekturis.common.application.transaction;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.repository.AggregateChangesCommiter;

import javax.inject.Named;
import java.util.ArrayList;
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
		loadedAggregates.set(new ArrayList<AggregateRoot>());
		return this;
	}

	public void trackAggregate(AggregateRoot aggregateRoot) {
		loadedAggregates().add(aggregateRoot);
	}

	@Override
	public void commit() {
		for (AggregateRoot aggregateRoot : loadedAggregates()) {
			aggregateChangesCommiter.commitChanges(aggregateRoot);
		}
	}

	private List<AggregateRoot> loadedAggregates() {
		if (loadedAggregates.get() == null) {
			throw new IllegalStateException("Transaction unit was not initialized for this thread");
		}

		return loadedAggregates.get();
	}
}
