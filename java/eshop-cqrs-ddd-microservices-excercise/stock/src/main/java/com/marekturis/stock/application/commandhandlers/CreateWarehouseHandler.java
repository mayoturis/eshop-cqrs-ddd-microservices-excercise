package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.Authorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.stock.application.commands.CreateWarehouse;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class CreateWarehouseHandler implements CommandHandler<CreateWarehouse> {

	private WarehouseRepository warehouseRepository;

	public CreateWarehouseHandler(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}

	@Transactional
	@Authorize(RoleTypes.ADMIN)
	public void handle(CreateWarehouse command) {
		Warehouse warehouse = new Warehouse(
				warehouseRepository.generateIdentity(),
				command.location()
		);

		warehouseRepository.add(warehouse);

		// todo fire event here
	}
}