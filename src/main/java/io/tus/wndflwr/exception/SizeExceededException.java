package io.tus.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class SizeExceededException extends TusException {
	public SizeExceededException(String message) {
		super(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, message);
	}
}
