package com.wndflwr.locker;

import com.wndflwr.exception.FileLockedException;
import org.springframework.boot.actuate.metrics.util.SimpleInMemoryRepository;
import org.springframework.stereotype.Component;

@Component
public class SimpleLocker implements Locker {

	private final SimpleInMemoryRepository<Lock> locks = new SimpleInMemoryRepository<>();

	@Override
	public synchronized Lock getLock(final String name) throws FileLockedException {
		try {
			return locks.update(name, new SimpleInMemoryRepository.Callback<Lock>() {
				@Override
				public Lock modify(Lock lock) {
					if (locks.findOne(name) != null) {
						throw new LockGetFailedException(name);
					}
					return add(name);
				}
			});
		} catch (LockGetFailedException e) {
			throw new FileLockedException(e.getLockName());
		}
	}

	private Lock add(String name) {
		Lock lock = new Lock(name, this);
		locks.set(name, lock);
		return lock;
	}

	@Override
	public synchronized boolean releaseLock(final String name) {
		final Lock lock = locks.findOne(name);
		if (lock == null) {
			return false;
		}

		locks.remove(name);
		return true;
	}
}
