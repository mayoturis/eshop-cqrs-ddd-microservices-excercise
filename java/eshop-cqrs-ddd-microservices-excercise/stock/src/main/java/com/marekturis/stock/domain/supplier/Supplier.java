package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.aggregate.AggregateRootBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Marek Turis
 */
public class Supplier extends AggregateRootBase {

	private static final long serialVersionUID = 587466325;

	private String name;

	private List<OfferedProduct> offeredProducts = new ArrayList<OfferedProduct>();

	public Supplier(String name, Integer identity) {
		super(identity);
	}

	public void addOfferedProduct(int productId, BigDecimal price) {
		if (productIsOffered(productId)) {
			throw new IllegalStateException("Product with given id is already offered");
		}

		fire(new NewProductOfferedBySupplier(identity(), productId, price));
	}

	public void removeOfferedProduct(int productId) {
		if (!productIsOffered(productId)) {
			throw new IllegalStateException("Product with given id is not offered");
		}

		fire(new OfferedProductRemovedFromSupplier(identity(), productId));
	}

	private boolean productIsOffered(int productId) {
		for (OfferedProduct offeredProduct : offeredProducts) {
			if (offeredProduct.getProductId() == productId) {
				return true;
			}
		}
		return false;
	}

	private void apply(NewProductOfferedBySupplier event) {
		offeredProducts.add(new OfferedProduct(event.getProductId(), event.getPrice()));
	}

	private void apply(OfferedProductRemovedFromSupplier event) {
		for (Iterator<OfferedProduct> i = offeredProducts.iterator(); i.hasNext();) {
			OfferedProduct offeredProduct = i.next();
			if (offeredProduct.getProductId() == event.getProductId()) {
				i.remove();
			}
		}
	}
}
