package io.tus.wndflwr.locker;

import io.tus.wndflwr.AbstractIntegrationTest;
import io.tus.wndflwr.exception.FileLockedException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class SimpleLockerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	@Qualifier("simpleLocker")
	private Locker sut;

	@Test(expected = FileLockedException.class)
	public void getLockShouldThrowAnExceptionWhenRaceCondition() throws FileLockedException {
		// given
		String lockName = UUID.randomUUID().toString();

		// when
		sut.getLock(lockName);
		sut.getLock(lockName);

		// then
		fail();
	}

	@Test
	public void getAndReleaseAndGetLockShouldNotThrowAnException() throws FileLockedException {
		// given
		String lockName = UUID.randomUUID().toString();
		String anotherLockName = UUID.randomUUID().toString();

		// when
		sut.getLock(lockName);
		sut.releaseLock(lockName);
		Lock result = sut.getLock(lockName);

		Lock anotherResult = sut.getLock(anotherLockName);

		// then
		assertThat(result.getName()).isEqualTo(lockName);
		assertThat(anotherResult.getName()).isEqualTo(anotherLockName);
	}
}
