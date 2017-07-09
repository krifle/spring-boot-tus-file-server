package io.tus.wndflwr.service;

import io.tus.wndflwr.controller.request.model.UserSearch;
import io.tus.wndflwr.controller.response.model.CommonResult;
import io.tus.wndflwr.repository.UserMapper;
import io.tus.wndflwr.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List<User> getUserList(UserSearch userSearch) {
		List<User> users = userMapper.selectUsers(userSearch);
		for (User user: users) {
			user.setIps(userMapper.selectUserIpByUserName(user.getUsername()));
			user.setAuthorities(userMapper.selectUserAuthorityByUserName(user.getUsername()));
		}
		return users;
	}

	public User getUser(String username, boolean cleansePassword) {
		User user = userMapper.selectUserByUsername(username);
		user.setIps(userMapper.selectUserIpByUserName(username));
		user.setAuthorities(userMapper.selectUserAuthorityByUserName(username));
		if (cleansePassword) {
			user.setPassword(null);
		}
		return user;
	}

	public CommonResult postUser(User user) {

		return null;
	}
}
