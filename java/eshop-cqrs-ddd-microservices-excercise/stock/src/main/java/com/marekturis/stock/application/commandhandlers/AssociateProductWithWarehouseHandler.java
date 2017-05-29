package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.stock.application.commands.AssociateProductWithWarehouse;
import com.marekturis.stock.domain.warehouse.ProductAssociatedWithWarehouse;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class AssociateProductWithWarehouseHandler implements CommandHandler<AssociateProductWithWarehouse> {

	private WarehouseRepository warehouseRepository;
	private EventPublisher eventPublisher;

	public AssociateProductWithWarehouseHandler(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}

	@Override
	@Transactional
	public void handle(AssociateProductWithWarehouse command) {
		int warehouseId = command.getWarehouseId();
		int productId = command.getProductId();

		Warehouse warehouse = warehouseRepository.getById(warehouseId);

		if (warehouse == null) {
			throw new IllegalStateException("Warehouse with given id doesn't exist");
		}

		warehouse.setWarehouseProductId(productId);
	}
}
