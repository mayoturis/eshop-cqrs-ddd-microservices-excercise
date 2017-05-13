package com.marekturis.stock.test.domain.supplier;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.stock.domain.supplier.NewProductOfferedBySupplier;
import com.marekturis.stock.domain.supplier.OfferedProductRemovedFromSupplier;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.test.AggregateRootTestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class WhenExistingProductIsRemoved extends AggregateRootTestBase<Supplier> {

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
		events.add(new NewProductOfferedBySupplier(SUPPLIER_ID, PRODUCT_ID, PRICE));
		return events;
	}

	@Override
	protected void when() {
		aggregateRoot.removeOfferedProduct(PRODUCT_ID);
	}

	@Test
	public void correctEventIsReturned() {
		Assert.assertEquals(1, events.size());
		Assert.assertTrue(events.get(0) instanceof OfferedProductRemovedFromSupplier);
	}

	@Test
	public void eventHasCorrectAttributes() {
		OfferedProductRemovedFromSupplier event = (OfferedProductRemovedFromSupplier) events.get(0);
		Assert.assertEquals(PRODUCT_ID, event.getProductId());
		Assert.assertEquals(SUPPLIER_ID, event.getSupplierId());
	}
}
