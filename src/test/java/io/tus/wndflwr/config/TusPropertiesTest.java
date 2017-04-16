package io.tus.wndflwr.config;

import io.tus.wndflwr.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TusPropertiesTest extends AbstractIntegrationTest {

	@Autowired
	private TusProperties sut;

	@Test
	public void testIfPropertiesWork() {
		assertThat(sut.getVersion()).isEqualTo("1.0.0");
		assertThat(sut.getMaxSize()).isEqualTo(0L);
	}
}