package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends TusException {

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, "Not found exception: " + message);
	}
}
