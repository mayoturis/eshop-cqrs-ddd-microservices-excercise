package com.marekturis.common.resource;

import com.marekturis.common.application.authorization.AuthenticationException;
import com.marekturis.common.application.authorization.AuthorizationException;
import com.marekturis.common.application.validation.NotFoundException;
import com.marekturis.common.application.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Marek Turis
 */
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	void handleException(AuthorizationException ex) {
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	void handleException(AuthenticationException ex) {
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	void handleException(ValidationException ex) {
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void handleException(NotFoundException ex) {
	}
}
