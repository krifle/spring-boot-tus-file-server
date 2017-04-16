package io.tus.wndflwr.locker;

import io.tus.wndflwr.exception.TusException;

public class Lock implements Comparable<Lock>, AutoCloseable {

	private String name;
	private Locker locker;

	public Lock(String name, Locker locker) {
		this.name = name;
		this.locker = locker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Lock other) {
		return name.compareTo(other.getName());
	}

	@Override
	public void close() throws TusException {
		locker.releaseLock(name);
	}
}
