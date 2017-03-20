package com.marekturis.stock.domain;

import com.marekturis.common.domain.aggregate.AggregateRootBase;

/**
 * @author Marek Turis
 */
public class Counter extends AggregateRootBase {

	//public static final long serialVersionUID = 2L;

	private int count = 0;

	public Counter(Integer identity) {
		super(identity);
	}

	public Counter(Integer identity, int version) {
		super(identity, version);
	}

	public void increase(int ammount) {
		if (ammount < 0) {
			throw new IllegalArgumentException("Ammount can't be less than zero");
		}

		fire(new CountIncreased(ammount));
	}

	private void apply(CountIncreased countIncreased) {
		count += countIncreased.ammount();
	}
}
