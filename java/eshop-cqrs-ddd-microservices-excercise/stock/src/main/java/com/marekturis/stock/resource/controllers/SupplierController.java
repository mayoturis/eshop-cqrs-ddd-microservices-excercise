package com.marekturis.stock.resource.controllers;

import com.marekturis.common.application.command.Command;
import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.common.resource.ControllerHelpers;
import com.marekturis.stock.application.commands.AddNewOfferedProductToSupplier;
import com.marekturis.stock.application.commands.CreateSupplier;
import com.marekturis.stock.application.commands.RemoveOfferedProductFromSupplier;
import com.marekturis.stock.resource.dtos.NewOfferedProductDto;
import com.marekturis.stock.resource.dtos.NewSupplierDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Marek Turis
 */
@RestController
@RequestMapping("/suppliers")
public class SupplierController {

	private CommandDispatcher commandDispatcher;

	public SupplierController(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public void newWarehouse(@RequestHeader(value="Authorization") String authorizationHeader, @RequestBody NewSupplierDto newSupplierDto) {
		Command command = new CreateSupplier(ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader), newSupplierDto.getName());
		commandDispatcher.dispatch(command);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, value = "/{supplierId}/products")
	public void addNewOfferedProduct(@RequestHeader(value="Authorization") String authorizationHeader, @RequestBody NewOfferedProductDto newOfferedProductDto, @PathVariable int supplierId) {
		Command command = new AddNewOfferedProductToSupplier(
			ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader),
			supplierId,
			newOfferedProductDto.getProductId(),
			newOfferedProductDto.getPrice()
		);
		commandDispatcher.dispatch(command);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{supplierId}/products/{productId}")
	public void removeOfferedProduct(@RequestHeader(value="Authorization") String authorizationHeader, @PathVariable int supplierId, @PathVariable int productId) {
		Command command = new RemoveOfferedProductFromSupplier(
				ControllerHelpers.tokenFromAuthorizationHeader(authorizationHeader),
				supplierId,
				productId
		);
		commandDispatcher.dispatch(command);
	}
}
