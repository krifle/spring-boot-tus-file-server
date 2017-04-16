package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class MismatchOffsetException extends TusException {

	public MismatchOffsetException(String message) {
		super(HttpStatus.CONFLICT, message);
	}
}
