package io.tus.wndflwr.config.security;

import io.tus.wndflwr.repository.UserMapper;
import io.tus.wndflwr.repository.model.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectUserByUsername(username);
		user.setAuthorities(userMapper.selectUserAuthorityByUserName(username));
		user.setIps(userMapper.selectUserIpByUserName(username));
		System.out.println("////");
		System.out.println(ToStringBuilder.reflectionToString(user));
		return user;
	}
}
