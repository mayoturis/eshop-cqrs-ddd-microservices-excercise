package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.CustomAuthorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.application.validation.ValidationException;
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
	@CustomAuthorize(RoleTypes.SALESMAN)
	public void handle(AddNewProductToWarehouse command) {
		Warehouse warehouse = warehouseRepository.getById(command.getWarehouseId());

		if (warehouse == null) {
			throw new ValidationException("Warehouse with given id doesn't exist");
		}

		warehouse.addNewProduct(command.getProductId());
	}
}
