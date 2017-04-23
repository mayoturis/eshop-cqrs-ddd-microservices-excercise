package com.marekturis.stock.domain.warehouse;

import com.marekturis.common.domain.aggregate.AggregateRootBase;


/**
 * @author Marek Turis
 */
public class Warehouse extends AggregateRootBase {

	private static final long serialVersionUID = 8745136785l;

	private String location;
	private Store store = new Store();

	public Warehouse(int identity, String location) {
		super(identity);
		this.location = location;
	}

	public void addNewProduct(int productId) {
		if (store.existProduct(productId)) {
			throw new IllegalArgumentException("Product already exist in warehouse");
		}

		fire(new NewProductAddedToWarehouse(identity(), productId));
	}

	private void apply(NewProductAddedToWarehouse event) {
		store.addProduct(event.getProductId());
	}

	public void increaseAmmountOfProduct(int productId, int ammount) {
		if (!store.existProduct(productId)) {
			throw new IllegalArgumentException("Product with given id doesn't exist in warehouse");
		}

		fire(new ProductAmmountInWarehouseIncreased(
			identity(),
			productId,
			ammount
		));
	}

	private void apply(ProductAmmountInWarehouseIncreased event) {
		store.increaseProductAmmount(event.getProductId(), event.getAmmount());
	}

	public void decreaseAmmountOfProduct(int productId, int ammount) {
		if (!store.existProduct(productId)) {
			throw new IllegalArgumentException("Product with given id doesn't exist in warehouse");
		}

		if (!store.enoughItemsInStore(productId, ammount)) {
			throw new IllegalArgumentException("There is not enough items of product "
					+ productId + " in warehouse to decrease by ammount " + ammount);
		}

		fire(new ProductAmmountInWarehouseDecreased(
				identity(),
				productId,
				ammount
		));
	}

	private void apply(ProductAmmountInWarehouseDecreased event) {
		store.decreaseProductAmmount(event.getProductId(), event.getAmmount());
	}
}
