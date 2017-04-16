package io.tus.wndflwr.exception;

import org.springframework.http.HttpStatus;

public class FileLockedException extends TusException {

	public FileLockedException(String message) {
		super(HttpStatus.LOCKED, "File currently locked: " + message);
	}
}
