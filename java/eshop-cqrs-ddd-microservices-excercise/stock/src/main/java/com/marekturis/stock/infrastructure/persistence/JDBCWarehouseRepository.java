package com.marekturis.stock.infrastructure.persistence;

import com.marekturis.common.domain.repository.GenericAggregateRepository;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Random;
import java.util.UUID;

/**
 * @author Marek Turis
 */
@Named
public class JDBCWarehouseRepository implements WarehouseRepository {

	private GenericAggregateRepository genericAggregateRepository;

	@Inject
	public JDBCWarehouseRepository(GenericAggregateRepository genericAggregateRepository) {
		this.genericAggregateRepository = genericAggregateRepository;
	}

	public Warehouse getById(Integer id) {
		return genericAggregateRepository.getById(id);
	}

	public void add(Warehouse aggregetRoot) {
		genericAggregateRepository.add(aggregetRoot);
	}

	public int generateIdentity() {
		Random random = new Random();
		return random.nextInt(Integer.MAX_VALUE) + 1;
	}
}
