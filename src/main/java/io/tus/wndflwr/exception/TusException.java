package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class TusException extends Exception {
	private HttpStatus httpStatus;
	private String message;

	protected TusException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}
}
