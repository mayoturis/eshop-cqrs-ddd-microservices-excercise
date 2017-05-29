package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.test.domain.AggregateRootTestBase;

/**
 * @author Marek Turis
 */
public abstract class WarehouseTestBase extends AggregateRootTestBase<Warehouse> {

	public static final int WAREHOUSE_ID = 5;
	public static final int PRODUCT_ID = 55;

	@Override
	protected Warehouse createAggregate() {
		return new Warehouse(WAREHOUSE_ID, "Brno");
	}
}
