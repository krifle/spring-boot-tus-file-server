package io.tus.wndflwr.service;

import io.tus.wndflwr.AbstractIntegrationTest;
import io.tus.wndflwr.controller.request.model.UserSearch;
import io.tus.wndflwr.repository.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private UserService sut;

	@Test
	public void test_getUserList() {
		// given
		UserSearch userSearch = new UserSearch();
		userSearch.setAccountNonExpired(true);
		userSearch.setAccountNonLocked(true);
		userSearch.setCredentialsNonExpired(true);
		userSearch.setEnabled(true);
		userSearch.setIp("127.0.0.1");

		// when
		List<User> result = sut.getUserList(userSearch);

		// then
		for (User user : result) {
			System.out.println(ToStringBuilder.reflectionToString(user, ToStringStyle.SIMPLE_STYLE));
		}
	}

	@Test
	public void test_getUser() {
		// given
		String username = "admin";

		// when
		User result = sut.getUser(username, false);

		// then
		System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.SIMPLE_STYLE));
	}
}