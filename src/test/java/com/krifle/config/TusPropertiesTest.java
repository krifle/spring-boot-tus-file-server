package com.krifle.config;

import com.krifle.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TusPropertiesTest extends AbstractIntegrationTest {

	@Autowired
	private TusProperties tusProperties;

	@Test
	public void testIfPropertiesWork() {
		assertThat(tusProperties.getVersion()).isEqualTo("1.0.0");
		assertThat(tusProperties.getMaxSize()).isEqualTo(0L);
	}
}