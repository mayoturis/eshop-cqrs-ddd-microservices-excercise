package com.marekturis.stock.domain.warehouse;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marek Turis
 */
public class Store implements Serializable {

	private Map<Integer, Integer> ammounts = new HashMap<Integer, Integer>();

	public boolean existProduct(Integer productId) {
		return ammounts.containsKey(productId);
	}

	public void increaseProductAmmount(int productId, int ammount) {
		ammounts.put(productId, ammounts.get(productId) + ammount);
	}

	public boolean enoughItemsInStore(int productId, int ammount) {
		return ammounts.get(productId) - ammount >= 0;
	}

	public void decreaseProductAmmount(int productId, int ammount) {
		ammounts.put(productId, ammounts.get(productId) - ammount);
	}

	public void addProduct(int productId) {
		ammounts.put(productId, 0);
	}
}
