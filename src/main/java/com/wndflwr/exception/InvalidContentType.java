package com.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InvalidContentType extends TusException {

	public InvalidContentType(String message) {
		super(HttpServletResponse.SC_BAD_GATEWAY, message);
	}
}
