package com.marekturis.stock.resource.controllers;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.common.resource.ControllerHelpers;
import com.marekturis.stock.application.commands.AddNewProductToWarehouse;
import com.marekturis.stock.application.commands.CreateWarehouse;
import com.marekturis.stock.application.commands.DecreaseProductAmmountInWarehouse;
import com.marekturis.stock.application.commands.IncreaseProductAmmountInWarehouse;
import com.marekturis.stock.resource.dtos.AddNewProductToWarehouseDto;
import com.marekturis.stock.resource.dtos.NewWarehouseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Marek Turis
 */
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {
	private CommandDispatcher commandDispatcher;

	public WarehouseController(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public void newWarehouse(@RequestHeader(value="Authorization") String authorizationHeader, @RequestBody NewWarehouseDto newWarehouseDto) {
		Command command = new CreateWarehouse(newWarehouseDto.getLocation(), ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader));
		commandDispatcher.dispatch(command);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.POST, value = "/{warehouseId}/products")
	public void addProductToWarehouse(
			@RequestHeader(value="Authorization") String authorizationHeader,
			@PathVariable int warehouseId,
			@RequestBody AddNewProductToWarehouseDto dto) {
		Command command = new AddNewProductToWarehouse(
				warehouseId,
				dto.getProductId(),
				ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader));

		commandDispatcher.dispatch(command);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.POST, value = "/{warehouseId}/products/{productId}/increaseAmmount/{ammount}")
	public void increaseProductAmmountInWarehouse(
			@RequestHeader(value="Authorization") String authorizationHeader,
			@PathVariable int warehouseId,
			@PathVariable int productId,
			@PathVariable int ammount) {
		Command command = new IncreaseProductAmmountInWarehouse(
				warehouseId,
				productId,
				ammount,
				ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader));

		commandDispatcher.dispatch(command);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.POST, value = "/{warehouseId}/products/{productId}/decreaseAmmount/{ammount}")
	public void decreaseProductAmmountInWarehouse(
			@RequestHeader(value="Authorization") String authorizationHeader,
			@PathVariable int warehouseId,
			@PathVariable int productId,
			@PathVariable int ammount) {
		Command command = new DecreaseProductAmmountInWarehouse(
				warehouseId,
				productId,
				ammount,
				ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader));

		commandDispatcher.dispatch(command);
	}
}
