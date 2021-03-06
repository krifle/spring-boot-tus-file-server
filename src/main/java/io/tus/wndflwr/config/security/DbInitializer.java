package io.tus.wndflwr.config.security;

import io.tus.wndflwr.repository.UserMapper;
import io.tus.wndflwr.repository.model.Authority;
import io.tus.wndflwr.repository.model.Ip;
import io.tus.wndflwr.repository.model.IpType;
import io.tus.wndflwr.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;

@Service
public class DbInitializer {

	private static final String DEFAULT_USERNAME = "admin";
	private static final String DEFAULT_PASSWORD = "admin";
	private static final String DEFAULT_USER_EMAIL = "admin@admin.com";
	private static final String DEFAULT_USER_AUTHORITY = "admin";
	private static final String DEFAULT_IP = "127.0.0.1";

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void initializeDb() {
		userMapper.createTableUser();
		userMapper.createTableUserAuthority();
		userMapper.createTableUserIp();

		createDefaultAdmin();
	}

	private void createDefaultAdmin() {
		if (userMapper.selectUserByUsername(DEFAULT_USERNAME) == null) {
			userMapper.insertUser(defaultUser());
		}
		if (CollectionUtils.isEmpty(userMapper.selectUserAuthorityByUserName(DEFAULT_USERNAME))) {
			userMapper.insertUserAuthority(defaultAuthority());
		}
		if (CollectionUtils.isEmpty(userMapper.selectUserIpByUserName(DEFAULT_USERNAME))) {
			userMapper.insertUserIp(defaultIp());
		}
	}

	private User defaultUser() {
		User user = new User();
		user.setUsername(DEFAULT_USERNAME);
		user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
		user.setEmail(DEFAULT_USER_EMAIL);
		return user;
	}

	private Authority defaultAuthority() {
		Authority authority = new Authority();
		authority.setUsername(DEFAULT_USERNAME);
		authority.setAuthority(DEFAULT_USER_AUTHORITY);
		return authority;
	}

	private Ip defaultIp() {
		Ip ip = new Ip();
		ip.setUsername(DEFAULT_USERNAME);
		ip.setIp(DEFAULT_IP);
		ip.setType(IpType.IPV4);
		return ip;
	}
}
