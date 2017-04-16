package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends TusException {

	public InternalServerException(String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}
}
