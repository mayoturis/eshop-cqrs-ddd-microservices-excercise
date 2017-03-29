package com.marekturis.stock.infrastructure.persistence;

import com.marekturis.common.domain.repository.GenericAggregateRepository;
import com.marekturis.stock.domain.supplier.Supplier;
import com.marekturis.stock.domain.supplier.SupplierRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Random;

/**
 * @author Marek Turis
 */
@Named
public class DefaultSupplierRepository implements SupplierRepository {

	private GenericAggregateRepository genericAggregateRepository;

	@Inject
	public DefaultSupplierRepository(GenericAggregateRepository genericAggregateRepository) {
		this.genericAggregateRepository = genericAggregateRepository;
	}

	public Supplier getById(Integer id) {
		return genericAggregateRepository.getById(id);
	}

	public void add(Supplier aggregetRoot) {
		genericAggregateRepository.add(aggregetRoot);
	}

	public int generateIdentity() {
		Random random = new Random();
		return random.nextInt(Integer.MAX_VALUE) + 1;
	}
}
