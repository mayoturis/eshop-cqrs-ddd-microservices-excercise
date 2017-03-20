package com.marekturis.common.domain.event;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author Marek Turis
 */
public class AggregateEventBase extends EventBase implements AggregateEvent {

	private static final long serialVersionUID = 5423686384936L;
	private Integer aggregateIdentity;
	private Integer aggregateVersion;

	@Override
	public int aggregateIdentity() {
		return aggregateIdentity;
	}

	@Override
	public int aggregateVersion() {
		return aggregateVersion;
	}

	@Override
	public void setAggregateIdentity(Integer id) {
		if (aggregateIdentity != null) {
			throw new IllegalStateException("Aggregate identity of event can be only set, not changed");
		}

		aggregateIdentity = id;
	}

	@Override
	public void setAggregateVersion(int version) {
		if (aggregateVersion != null) {
			throw new IllegalStateException("Aggregate version of event can be only set, not changed");
		}

		this.aggregateVersion = version;
	}
}
