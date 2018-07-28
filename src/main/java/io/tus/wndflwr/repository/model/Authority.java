package io.tus.wndflwr.repository.model;

import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class Authority implements GrantedAuthority {

	private static final String DEFAULT_ADMIN_AUTHORITY = "admin";
	private static final String DEFAULT_USER_AUTHORITY = "user";

	private String username;
	private String authority;

	public Authority() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean isAdmin() {
		return DEFAULT_ADMIN_AUTHORITY.equals(authority);
	}

	public static List<Authority> ofDefaults() {
		Authority adminAuthority = new Authority();
		adminAuthority.username = User.DEFAULT_USERNAME;
		adminAuthority.authority = DEFAULT_ADMIN_AUTHORITY;

		Authority userAuthority = new Authority();
		userAuthority.username = User.DEFAULT_USERNAME;
		userAuthority.authority = DEFAULT_USER_AUTHORITY;

		return Lists.newArrayList(adminAuthority, userAuthority);
	}

	public static Authority of(String username, String authority) {
		Authority authorityObject = new Authority();
		authorityObject.username = username;
		authorityObject.authority = authority;
		return authorityObject;
	}
}
