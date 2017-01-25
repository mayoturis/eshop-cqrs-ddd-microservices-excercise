package com.marekturis.identity.resource.controllers;

import com.marekturis.identity.application.AuthorizationException;
import com.marekturis.identity.application.RoleService;
import com.marekturis.identity.application.UserService;
import com.marekturis.identity.application.dto.AuthenticateUserDTO;
import com.marekturis.identity.application.dto.ChangeUserRoleDTO;
import com.marekturis.identity.application.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @author Marek Turis
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Inject
	private UserService userService;

	@Inject
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticateUserDTO authenticateUserDTO) {
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

	@RequestMapping(method = RequestMethod.POST,
			value = "/changeRole",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity changerRole(@Valid @RequestBody ChangeUserRoleDTO changeUserRoleDTO) {
		try {
			roleService.changeUserRole(changeUserRoleDTO);
		} catch(AuthorizationException ex) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

}
