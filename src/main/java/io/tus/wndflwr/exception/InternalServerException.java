package io.tus.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InternalServerException extends TusException {

	public InternalServerException(String message) {
		super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
	}
}
