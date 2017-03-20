package com.marekturis.stock.resource.controllers;

import com.marekturis.common.application.command.CommandDispatcher;
import com.marekturis.stock.application.CreateCounterCommand;
import com.marekturis.stock.application.IncreaseCounterCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Marek Turis
 */
@RestController
@RequestMapping("/dlhsie")
public class SkuskaController {

	private CommandDispatcher commandDispatcher;

	public SkuskaController(CommandDispatcher commandDispatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, value = "nieco/{id}")
	public String nieco(@PathVariable int id) {
		commandDispatcher.dispatch(new CreateCounterCommand(id));
		return "created correctly";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, value = "ine/{id}/{by}")
	public String ine(@PathVariable int id, @PathVariable int by) {
		commandDispatcher.dispatch(new IncreaseCounterCommand(by, id));
		return "updated correctly";
	}
}
