package io.tus.wndflwr.config.security;

import io.tus.wndflwr.repository.UserMapper;
import io.tus.wndflwr.repository.model.Authority;
import io.tus.wndflwr.repository.model.Ip;
import io.tus.wndflwr.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;

@Service
public class DbInitializer {

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
		if (userMapper.selectUserByUsername(User.DEFAULT_USERNAME) == null) {
			userMapper.insertUser(User.ofDefault(passwordEncoder));
		}
		if (CollectionUtils.isEmpty(userMapper.selectUserAuthorityByUserName(User.DEFAULT_USERNAME))) {
			userMapper.insertUserAuthority(Authority.ofDefault());
		}
		if (CollectionUtils.isEmpty(userMapper.selectUserIpByUserName(User.DEFAULT_USERNAME))) {
			userMapper.insertUserIp(Ip.ofDefault());
		}
	}
}
