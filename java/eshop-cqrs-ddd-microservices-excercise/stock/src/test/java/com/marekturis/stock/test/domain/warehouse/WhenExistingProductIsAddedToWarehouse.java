package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.stock.domain.warehouse.NewProductAddedToWarehouse;
import com.marekturis.stock.domain.warehouse.exceptions.ProductAlreadyInWarehouseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Turis
 */
public class WhenExistingProductIsAddedToWarehouse extends WarehouseTestBase {

	@Override
	protected List<AggregateEvent> given() {
		List<AggregateEvent> events = new ArrayList<>();
		events.add(new NewProductAddedToWarehouse(WAREHOUSE_ID, PRODUCT_ID));
		return events;
	}

	@Override
	protected void when() {
		aggregateRoot.addNewProduct(PRODUCT_ID);
	}

	@Test
	public void correctExceptionWasTrown() {
		assertCorrectExceptionWasTrown(ProductAlreadyInWarehouseException.class);
	}
}
