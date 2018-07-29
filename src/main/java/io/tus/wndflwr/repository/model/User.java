package io.tus.wndflwr.repository.model;

import com.google.common.base.Splitter;
import io.tus.wndflwr.controller.request.model.AuthorityList;
import io.tus.wndflwr.exception.UserManageException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class User implements UserDetails {

	public static final String DEFAULT_USERNAME = "admin";
	private static final String DEFAULT_UESR_PASSWORD = "admin";
	private static final String DEFAULT_USER_EMAIL = "admin@admin.com";

	private String username;
	private String password;
	private String email;
	private List<Ip> ips;
	private Date modDate;
	private Date regDate;
	private Date lastLogin;
	private List<Authority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Ip> getIps() {
		return ips;
	}

	public void setIps(List<Ip> ips) {
		this.ips = ips;
	}

	public void setIps(String ipList) {
		ips = Splitter
				.onPattern("\r?\n")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(ipList)
				.stream()
				.map(ip -> Ip.ofIpv4(username, ip))
				.collect(Collectors.toList());
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public void setAuthorities(AuthorityList authorityList) {
		authorities = authorityList.asAuthorityList(username);
	}

	public List<String> getAuthorityNames() {
		return authorities.stream().map(Authority::getAuthority).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean hasAdminAuthority() {
		return authorities.stream().anyMatch(Authority::isAdmin);
	}

	public boolean isDefaultAdmin() {
		return DEFAULT_USERNAME.equals(username);
	}

	public void validateForUpsert() {
		if (StringUtils.isEmpty(username)) {
			throw new UserManageException("Invalid input: Empty username");
		}
		if (StringUtils.isEmpty(email)) {
			throw new UserManageException("Invalid input: Empty email");
		}
		if (CollectionUtils.isEmpty(ips)) {
			throw new UserManageException("Invalid input: user's ip should not be empty.");
		}
		if (CollectionUtils.isEmpty(authorities)) {
			throw new UserManageException("Invalid input: user's authorities should not be empty");
		}
	}

	public static User ofDefault(PasswordEncoder passwordEncoder) {
		User user = new User();
		user.username = DEFAULT_USERNAME;
		user.password = passwordEncoder.encode(DEFAULT_UESR_PASSWORD);
		user.email = DEFAULT_USER_EMAIL;
		return user;
	}
}
