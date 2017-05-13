package com.marekturis.stock.test.domain.warehouse;

import com.marekturis.stock.domain.warehouse.exceptions.ProductNotInWarehouseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marek Turis
 */
public class WhenAmmountOfNonExistingProductIsIncreased extends WarehouseTestBase {

	public static final int AMMOUNT = 54;

	@Override
	protected void when() {
		aggregateRoot.increaseAmmountOfProduct(PRODUCT_ID, AMMOUNT);
	}

	@Test
	public void correctExceptionIsTrown() {
		assertCorrectExceptionWasTrown(ProductNotInWarehouseException.class);
	}
}
