package com.marekturis.stock.test;

import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.AggregateEvent;
import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public abstract class AggregateRootTestBase<TAggregateRoot extends AggregateRoot> {
	protected TAggregateRoot aggregateRoot;
	protected Throwable caught;
	protected List<AggregateEvent> events;

	protected abstract TAggregateRoot createAggregate();
	protected List<AggregateEvent> given() {
		return new ArrayList<>();
	}
	protected abstract void when();

	@Before
	public void setUp() {
		aggregateRoot = createAggregate();
		for (AggregateEvent event : given()) {
			aggregateRoot.replayEvent(event);
		}
		try {
			when();
			events = aggregateRoot.pullChanges();
		} catch (Throwable exepction) {
			caught = exepction;
		}
	}

	protected void assertEventType(Class classType) {
		Assert.assertEquals(1, events.size());
		Assert.assertTrue(classType.isInstance(events.get(0)));
	}

	protected void assertCorrectExceptionWasTrown(Class exceptionType) {
		Assert.assertTrue(exceptionType.isInstance(caught));
	}
}
