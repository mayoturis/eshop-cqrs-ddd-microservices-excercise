package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.stock.domain.warehouse.NewProductAddedToWarehouse;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marek Turis
 */
public class WhenNewProductIsAddedToWarehouse extends WarehouseTestBase {

	@Override
	protected void when() {
		aggregateRoot.addNewProduct(PRODUCT_ID);
	}

	@Test
	public void correctEventIsReturned() {
		assertEventType(NewProductAddedToWarehouse.class);
	}

	@Test
	public void eventHasCorrectAttributes() {
		NewProductAddedToWarehouse event = (NewProductAddedToWarehouse) events.get(0);
		Assert.assertEquals(PRODUCT_ID, event.getProductId());
		Assert.assertEquals(WAREHOUSE_ID, event.getWarehouseId());
	}
}
