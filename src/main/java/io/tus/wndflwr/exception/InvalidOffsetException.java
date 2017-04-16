package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class InvalidOffsetException extends TusException {

	public InvalidOffsetException(String offset) {
		super(HttpStatus.BAD_REQUEST, "Invalid offset: " + offset);
	}
}
