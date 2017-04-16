package io.tus.wndflwr.exception;

import javax.servlet.http.HttpServletResponse;

public class InvalidContentLengthException extends TusException {

	public InvalidContentLengthException(String contentLength) {
		super(HttpServletResponse.SC_BAD_REQUEST, "Invalid content length: " + contentLength);
	}
}
