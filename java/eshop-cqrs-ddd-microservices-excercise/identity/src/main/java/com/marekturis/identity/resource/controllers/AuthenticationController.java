package com.marekturis.identity.resource.controllers;

import com.marekturis.identity.application.UserService;
import com.marekturis.identity.application.dto.AuthenticateUserDTO;
import com.marekturis.identity.application.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author Marek Turis
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authenticate(@RequestBody AuthenticateUserDTO authenticateUserDTO) {
		String token = userService.authenticate(authenticateUserDTO);

		if (token == null) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	@RequestMapping(value = "/{authenticationToken}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUserByAuthenticationToken(@PathVariable String authenticationToken) {
		UserDTO user = userService.getUserByAuthenticationToken(authenticationToken);
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
}
