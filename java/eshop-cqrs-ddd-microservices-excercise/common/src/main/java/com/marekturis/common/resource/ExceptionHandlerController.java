package com.marekturis.common.resource;

import com.marekturis.common.application.authorization.AuthenticationException;
import com.marekturis.common.application.authorization.AuthorizationException;
import com.marekturis.common.application.validation.NotFoundException;
import com.marekturis.common.application.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Marek Turis
 */
@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public String handleException(AuthorizationException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public String handleException(AuthenticationException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleException(ValidationException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleException(NotFoundException ex) {
	}
}
