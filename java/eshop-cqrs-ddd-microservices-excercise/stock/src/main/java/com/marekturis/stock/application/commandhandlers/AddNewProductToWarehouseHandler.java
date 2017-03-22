package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.application.commands.AddNewProductToWarehouse;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class AddNewProductToWarehouseHandler implements CommandHandler<AddNewProductToWarehouse> {

	private WarehouseRepository warehouseRepository;

	public AddNewProductToWarehouseHandler(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}

	@Transactional
	@Authorize(RoleTypes.SALESMAN)
	public void handle(AddNewProductToWarehouse command) {
		Warehouse warehouse = warehouseRepository.getById(command.getWarehouseId());

		warehouse.addNewProduct(command.getProductId());
	}
}
