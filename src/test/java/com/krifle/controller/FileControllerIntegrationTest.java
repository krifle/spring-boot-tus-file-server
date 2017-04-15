package com.krifle.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class FileControllerIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void testTheTestMethod() {
		String result = testRestTemplate.getForObject("/files", String.class);
		assertThat(result).isEqualTo("tusVersion = 1.0.0");
	}
}