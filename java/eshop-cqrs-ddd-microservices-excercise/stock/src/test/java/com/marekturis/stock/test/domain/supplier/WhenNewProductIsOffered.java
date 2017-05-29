package com.marekturis.stock.test.domain.supplier;

import com.marekturis.stock.domain.supplier.NewProductOfferedBySupplier;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.test.domain.AggregateRootTestBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marek Turis
 */
public class WhenNewProductIsOffered extends AggregateRootTestBase<Supplier> {

	public static final int SUPPLIER_ID = 5;
	public static final int PRODUCT_ID = 11;
	public static final double PRICE = 55.5;

	@Override
	protected Supplier createAggregate() {
		return new Supplier("SKF", SUPPLIER_ID);
	}

	@Override
	protected void when() {
		aggregateRoot.addOfferedProduct(PRODUCT_ID, PRICE);
	}

	@Test
	public void correctEventIsPublished() {
		Assert.assertEquals(1, events.size());
		Assert.assertTrue(events.get(0) instanceof NewProductOfferedBySupplier);
	}

	@Test
	public void eventHasCorrectAtributes() {
		NewProductOfferedBySupplier event = (NewProductOfferedBySupplier) events.get(0);
		Assert.assertEquals(PRODUCT_ID, event.getProductId());
		Assert.assertEquals(SUPPLIER_ID, event.getSupplierId());
		Assert.assertEquals(PRICE, event.getPrice(), 0.005);
	}
}
