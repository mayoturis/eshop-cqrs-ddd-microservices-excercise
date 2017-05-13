package com.marekturis.stock.test.domain.supplier;

import com.marekturis.common.domain.aggregate.AggregateRootBase;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.domain.supplier.exceptions.ProductAlreadyOfferedBySupplierException;
import com.marekturis.stock.domain.supplier.exceptions.ProductNotOfferedBySupplierException;
import com.marekturis.stock.test.AggregateRootTestBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marek Turis
 */
public class WhenNonExistingProductIsRemoved extends AggregateRootTestBase<Supplier> {

	public static final int SUPPLIER_ID = 5;
	public static final int PRODUCT_ID = 11;

	@Override
	protected Supplier createAggregate() {
		return new Supplier("SKF", SUPPLIER_ID);
	}

	@Override
	protected void when() {
		aggregateRoot.removeOfferedProduct(PRODUCT_ID);
	}

	@Test
	public void correctExceptionIsTrown() {
		Assert.assertTrue(caught instanceof ProductNotOfferedBySupplierException);
	}
}
