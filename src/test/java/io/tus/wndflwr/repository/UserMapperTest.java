package io.tus.wndflwr.repository;

import io.tus.wndflwr.AbstractIntegrationTest;
import io.tus.wndflwr.repository.model.Authority;
import io.tus.wndflwr.repository.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest extends AbstractIntegrationTest {

	@Autowired
	private UserMapper sut;

	@Test
	public void test_selectUserByUserName() {
		// given
		String username = "admin";

		// when
		User result = sut.selectUserByUsername(username);

		// then
		assertThat(result.getUsername()).isEqualTo(username);
	}

	@Test
	public void test_selectAuthorityByUserName() {
		// given
		String username = "admin";

		// when
		List<Authority> result = sut.selectUserAuthorityByUserName(username);

		// then
		assertThat(result).isNotNull();
	}
}
