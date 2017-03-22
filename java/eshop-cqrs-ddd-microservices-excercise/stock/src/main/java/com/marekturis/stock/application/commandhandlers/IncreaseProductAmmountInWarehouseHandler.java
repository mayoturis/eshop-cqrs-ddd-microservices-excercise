package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.application.commands.IncreaseProductAmmountInWarehouse;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class IncreaseProductAmmountInWarehouseHandler implements CommandHandler<IncreaseProductAmmountInWarehouse> {

	private WarehouseRepository warehouseRepository;

	public IncreaseProductAmmountInWarehouseHandler(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}

	@Transactional
	@Authorize(RoleTypes.SALESMAN)
	public void handle(IncreaseProductAmmountInWarehouse command) {
		Warehouse warehouse = warehouseRepository.getById(command.getWarehouseId());

		warehouse.increaseAmmountOfProduct(command.getProductId(), command.getAmmount());
	}
}
