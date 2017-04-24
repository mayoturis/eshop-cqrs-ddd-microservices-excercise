package com.marekturis.stock.application.commandhandlers;

import com.marekturis.common.application.authorization.CustomAuthorize;
import com.marekturis.common.application.command.CommandHandler;
import com.marekturis.common.application.transaction.Transactional;
import com.marekturis.common.domain.RoleTypes;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.stock.application.commands.CreateWarehouse;
import com.marekturis.stock.domain.warehouse.Warehouse;
import com.marekturis.stock.domain.warehouse.WarehouseCreated;
import com.marekturis.stock.domain.warehouse.WarehouseRepository;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class CreateWarehouseHandler implements CommandHandler<CreateWarehouse> {

	private WarehouseRepository warehouseRepository;

	private EventPublisher eventPublisher;

	public CreateWarehouseHandler(WarehouseRepository warehouseRepository, EventPublisher eventPublisher) {
		this.warehouseRepository = warehouseRepository;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	@CustomAuthorize(RoleTypes.ADMIN)
	public void handle(CreateWarehouse command) {
		int identity = warehouseRepository.generateIdentity();
		Warehouse warehouse = new Warehouse(
				identity,
				command.location()
		);

		warehouseRepository.add(warehouse);

		eventPublisher.publish(new WarehouseCreated(identity, command.location()));
	}
}
