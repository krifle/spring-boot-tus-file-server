package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class InvalidUploadLengthException extends TusException {

	public InvalidUploadLengthException(String uploadLength) {
		super(HttpStatus.BAD_REQUEST, "Invalid Upload-Length header: " + uploadLength);
	}
}
