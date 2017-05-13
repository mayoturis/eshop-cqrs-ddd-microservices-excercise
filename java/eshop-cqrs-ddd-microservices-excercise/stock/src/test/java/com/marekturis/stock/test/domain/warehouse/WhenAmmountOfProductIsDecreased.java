package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.stock.domain.warehouse.NewProductAddedToWarehouse;
import com.marekturis.stock.domain.warehouse.ProductAmmountInWarehouseDecreased;
import com.marekturis.stock.domain.warehouse.ProductAmmountInWarehouseIncreased;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class WhenAmmountOfProductIsDecreased extends WarehouseTestBase {

	public static final int AMMOUNT = 54;

	@Override
	protected List<AggregateEvent> given() {
		List<AggregateEvent> events = new ArrayList<>();
		events.add(new NewProductAddedToWarehouse(WAREHOUSE_ID, PRODUCT_ID));
		events.add(new ProductAmmountInWarehouseIncreased(WAREHOUSE_ID, PRODUCT_ID, 1000));
		return events;
	}

	@Override
	protected void when() {
		aggregateRoot.decreaseAmmountOfProduct(PRODUCT_ID, AMMOUNT);
	}

	@Test
	public void eventIsOfCorrectType() {
		assertEventType(ProductAmmountInWarehouseDecreased.class);
	}

	@Test
	public void eventHasCorrectAttributes() {
		ProductAmmountInWarehouseDecreased event = (ProductAmmountInWarehouseDecreased) events.get(0);
		Assert.assertEquals(PRODUCT_ID, event.getProductId());
		Assert.assertEquals(WAREHOUSE_ID, event.getWarehouseId());
		Assert.assertEquals(AMMOUNT, event.getAmmount());
	}
}
