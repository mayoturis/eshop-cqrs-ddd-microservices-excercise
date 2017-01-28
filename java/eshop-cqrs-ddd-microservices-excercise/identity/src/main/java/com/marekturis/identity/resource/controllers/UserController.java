package com.marekturis.identity.resource.controllers;

import com.marekturis.identity.application.UserService;
import com.marekturis.identity.application.dto.RegisterUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @author Marek Turis
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Inject
	private UserService userService;

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
		userService.addUser(registerUserDTO);
	}
}
