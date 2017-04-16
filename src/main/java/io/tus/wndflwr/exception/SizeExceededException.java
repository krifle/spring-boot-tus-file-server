package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class SizeExceededException extends TusException {
	public SizeExceededException(String message) {
		super(HttpStatus.PAYLOAD_TOO_LARGE, message);
	}
}
