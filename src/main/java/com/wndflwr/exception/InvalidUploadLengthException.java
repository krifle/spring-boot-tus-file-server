package com.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InvalidUploadLengthException extends TusException {

	public InvalidUploadLengthException(String uploadLength) {
		super(HttpServletResponse.SC_BAD_REQUEST, "Invalid Upload-Length header: " + uploadLength);
	}
}
