package io.tus.wndflwr.repository.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	private static final String DEFAULT_USER_AUTHORITY = "admin";

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

	public static Authority ofDefault() {
		Authority authority = new Authority();
		authority.username = User.DEFAULT_USERNAME;
		authority.authority = DEFAULT_USER_AUTHORITY;
		return authority;
	}
}
