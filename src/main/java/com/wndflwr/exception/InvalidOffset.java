package com.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InvalidOffset extends TusException {

	public InvalidOffset(String offset) {
		super(HttpServletResponse.SC_BAD_REQUEST, "Invalid offset: " + offset);
	}
}
