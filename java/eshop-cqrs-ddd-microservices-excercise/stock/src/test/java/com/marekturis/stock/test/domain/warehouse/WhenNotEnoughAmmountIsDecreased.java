package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.stock.domain.warehouse.NewProductAddedToWarehouse;
import com.marekturis.stock.domain.warehouse.ProductAmmountInWarehouseIncreased;
import com.marekturis.stock.domain.warehouse.exceptions.NotEnoughItemsInStoreException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class WhenNotEnoughAmmountIsDecreased extends WarehouseTestBase {

	@Override
	protected List<AggregateEvent> given() {
		List<AggregateEvent> events = new ArrayList<>();
		events.add(new NewProductAddedToWarehouse(WAREHOUSE_ID, PRODUCT_ID));
		events.add(new ProductAmmountInWarehouseIncreased(WAREHOUSE_ID, PRODUCT_ID, 10));
		return events;
	}

	@Override
	protected void when() {
		aggregateRoot.decreaseAmmountOfProduct(PRODUCT_ID, 50);
	}

	@Test
	public void correctExceptionWasTrown() {
		assertCorrectExceptionWasTrown(NotEnoughItemsInStoreException.class);
	}

}
