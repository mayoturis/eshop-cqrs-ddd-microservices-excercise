package com.marekturis.stock.domain.supplier;

import com.marekturis.common.domain.repository.SpecificAggregateRepository;

/**
 * @author Marek Turis
 */
public interface SupplierRepository extends SpecificAggregateRepository<Supplier> {
	int generateIdentity();
}
