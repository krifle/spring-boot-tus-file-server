package io.tus.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InvalidOffsetException extends TusException {

	public InvalidOffsetException(String offset) {
		super(HttpServletResponse.SC_BAD_REQUEST, "Invalid offset: " + offset);
	}
}
