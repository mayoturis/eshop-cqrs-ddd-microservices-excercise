package com.marekturis.stock.domain;

import com.marekturis.common.domain.event.AggregateEventBase;

/**
 * @author Marek Turis
 */
public class CountIncreased extends AggregateEventBase {

	private int ammount;

	public CountIncreased(int ammount) {
		this.ammount = ammount;
	}

	public int ammount() {
		return ammount;
	}
}
