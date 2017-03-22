package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.aggregate.AggregateRootBase;

import java.util.List;

/**
 * @author Marek Turis
 */
public class Supplier extends AggregateRootBase {


	private List<Integer> offeredProducts;

	public Supplier(Integer identity) {
		super(identity);
	}
}
