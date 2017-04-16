package com.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InvalidContentTypeException extends TusException {

	public InvalidContentTypeException(String message) {
		super(HttpServletResponse.SC_BAD_GATEWAY, message);
	}
}
