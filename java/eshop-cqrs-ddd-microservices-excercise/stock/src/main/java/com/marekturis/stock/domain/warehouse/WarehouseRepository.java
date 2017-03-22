package com.marekturis.stock.domain.warehouse;

import com.marekturis.common.domain.repository.SpecificAggregateRepository;

/**
 * @author Marek Turis
 */
public interface WarehouseRepository extends SpecificAggregateRepository<Warehouse> {
	int generateIdentity();
}
