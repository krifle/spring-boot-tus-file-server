package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class InvalidContentLengthException extends TusException {

	public InvalidContentLengthException(String contentLength) {
		super(HttpStatus.BAD_REQUEST, "Invalid content length: " + contentLength);
	}
}
