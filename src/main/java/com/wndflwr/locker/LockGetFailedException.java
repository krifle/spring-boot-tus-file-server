package com.wndflwr.locker;

public class LockGetFailedException extends RuntimeException {

	private String lockName;

	public LockGetFailedException(String lockName) {
		this.lockName = lockName;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}
}
