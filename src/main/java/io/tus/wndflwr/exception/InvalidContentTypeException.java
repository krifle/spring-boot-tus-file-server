package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class InvalidContentTypeException extends TusException {

	public InvalidContentTypeException(String message) {
		super(HttpStatus.BAD_GATEWAY, message);
	}
}
