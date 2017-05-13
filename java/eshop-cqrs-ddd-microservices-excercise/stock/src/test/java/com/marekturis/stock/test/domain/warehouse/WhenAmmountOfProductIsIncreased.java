package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.stock.domain.warehouse.NewProductAddedToWarehouse;
import com.marekturis.stock.domain.warehouse.ProductAmmountInWarehouseIncreased;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class WhenAmmountOfProductIsIncreased extends WarehouseTestBase {

	public static final int AMMOUNT = 54;

	@Override
	protected List<AggregateEvent> given() {
		List<AggregateEvent> events = new ArrayList<>();
		events.add(new NewProductAddedToWarehouse(WAREHOUSE_ID, PRODUCT_ID));
		return events;
	}

	@Override
	protected void when() {
		aggregateRoot.increaseAmmountOfProduct(PRODUCT_ID, AMMOUNT);
	}

	@Test
	public void eventIsOfCorrectType() {
		assertEventType(ProductAmmountInWarehouseIncreased.class);
	}

	@Test
	public void eventHasCorrectAttributes() {
		ProductAmmountInWarehouseIncreased event = (ProductAmmountInWarehouseIncreased) events.get(0);
		Assert.assertEquals(PRODUCT_ID, event.getProductId());
		Assert.assertEquals(WAREHOUSE_ID, event.getWarehouseId());
		Assert.assertEquals(AMMOUNT, event.getAmmount());
	}
}
