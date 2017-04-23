package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.application.commands.DecreaseProductAmmountInWarehouse;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class DecreaseProductAmmountInWarehouseHandler implements CommandHandler<DecreaseProductAmmountInWarehouse> {

	private WarehouseRepository warehouseRepository;

	public DecreaseProductAmmountInWarehouseHandler(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}

	@Transactional
	@Authorize(RoleTypes.SALESMAN)
	public void handle(DecreaseProductAmmountInWarehouse command) {
		Warehouse warehouse = warehouseRepository.getById(command.getWarehouseId());

		if (warehouse == null) {
			throw new IllegalArgumentException("Warehouse with given ID doesn't exist");
		}

		warehouse.decreaseAmmountOfProduct(command.getProductId(), command.getAmmount());
	}
}
