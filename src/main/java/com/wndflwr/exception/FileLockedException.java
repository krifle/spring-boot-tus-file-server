package com.wndflwr.exception;

public class FileLockedException extends TusException {

	public FileLockedException(String message) {
		super(423, "File currently locked: " + message);
	}
}
