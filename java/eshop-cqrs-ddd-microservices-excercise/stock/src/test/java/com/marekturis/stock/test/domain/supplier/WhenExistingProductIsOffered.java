package com.marekturis.stock.test.domain.supplier;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.stock.domain.supplier.NewProductOfferedBySupplier;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.domain.supplier.exceptions.ProductAlreadyOfferedBySupplierException;
import com.marekturis.stock.test.AggregateRootTestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class WhenExistingProductIsOffered extends AggregateRootTestBase<Supplier> {
	public static final int SUPPLIER_ID = 5;
	public static final int PRODUCT_ID = 11;
	public static final double PRICE = 55.5;

	@Override
	protected Supplier createAggregate() {
		return new Supplier("SKF", SUPPLIER_ID);
	}

	@Override
	protected List<AggregateEvent> given() {
		List<AggregateEvent> events = new ArrayList<>();
		events.add(new NewProductOfferedBySupplier(SUPPLIER_ID, PRODUCT_ID, 25));
		return events;
	}

	@Override
	protected void when() {
		aggregateRoot.addOfferedProduct(PRODUCT_ID, PRICE);
	}

	@Test
	public void correctExceptionIsThrown() {
		Assert.assertTrue(caught instanceof ProductAlreadyOfferedBySupplierException);
	}
}
