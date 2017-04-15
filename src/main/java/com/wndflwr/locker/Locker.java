package com.wndflwr.locker;

import com.wndflwr.exception.FileLockedException;

/**
 * Locker mechanism should be easily replaced.
 * In clustered system, SimpleLocker must be replaced by mechanism such as Redis based lock.
 */
public interface Locker {

	Lock getLock(String name) throws FileLockedException;
	boolean releaseLock(String name);
}
