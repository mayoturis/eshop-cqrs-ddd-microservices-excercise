package com.marekturis.identity.resource.controllers;

import com.marekturis.common.application.authorization.AuthorizationException;
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

	@RequestMapping(value = "{authenticationToken}/belongsTo/{userId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity tokenBelongsTo(@PathVariable String authenticationToken, @PathVariable Integer userId) {
		if (userService.tokenBelongsToUser(authenticationToken, userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/{authenticationToken}/inRole/{roleName}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUserInRole(
			@PathVariable String authenticationToken,
			@PathVariable String roleName) {
		UserDTO user = userService.getUserInRole(authenticationToken, roleName);
		if (user == null) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST,
			value = "/changeRole",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity changeRole(@Valid @RequestBody ChangeUserRoleDTO changeUserRoleDTO) {
		roleService.changeUserRole(changeUserRoleDTO);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
