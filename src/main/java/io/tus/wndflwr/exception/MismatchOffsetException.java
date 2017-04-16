package io.tus.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class MismatchOffsetException extends TusException {

	public MismatchOffsetException(String message) {
		super(HttpServletResponse.SC_CONFLICT, message);
	}
}
