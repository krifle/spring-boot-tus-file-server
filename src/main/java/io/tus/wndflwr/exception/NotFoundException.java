package io.tus.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class NotFoundException extends TusException {

	public NotFoundException(String message) {
		super(HttpServletResponse.SC_NOT_FOUND, "Not found exception: " + message);
	}
}
