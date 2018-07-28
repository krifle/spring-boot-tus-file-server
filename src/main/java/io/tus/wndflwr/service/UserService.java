package io.tus.wndflwr.service;

import io.tus.wndflwr.controller.request.model.UserSearch;
import io.tus.wndflwr.controller.response.model.CommonResult;
import io.tus.wndflwr.repository.UserMapper;
import io.tus.wndflwr.repository.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List<User> getUserList(UserSearch userSearch) {
		List<User> users = userMapper.selectUsers(userSearch);
		for (User user : users) {
			user.setIps(userMapper.selectUserIpByUsername(user.getUsername()));
			user.setAuthorities(userMapper.selectUserAuthorityByUserName(user.getUsername()));
		}
		return users;
	}

	public User getUser(String username, boolean cleansePassword) {
		if (StringUtils.isEmpty(username)) {
			return new User();
		}
		User user = userMapper.selectUserByUsername(username);
		user.setIps(userMapper.selectUserIpByUsername(username));
		user.setAuthorities(userMapper.selectUserAuthorityByUserName(username));
		if (cleansePassword) {
			user.setPassword(null);
		}
		return user;
	}

	public void upsertUser(User user) {
		user.validateForUpsert();

		if (userMapper.selectUserByUsername(user.getUsername()) == null) {
			userMapper.insertUser(user);
			user.getAuthorities().forEach(authority -> userMapper.insertUserAuthority(authority));
			user.getIps().forEach(ip -> userMapper.insertUserIp(ip));
		} else {
			userMapper.updateUser(user);
			userMapper.deleteUserIpByUsername(user.getUsername());
			userMapper.deleteUserAuthorityByUsername(user.getUsername());
			user.getAuthorities().forEach(authority -> userMapper.insertUserAuthority(authority));
			user.getIps().forEach(ip -> userMapper.insertUserIp(ip));
		}
	}

	public CommonResult postUser(User user) {

		return null;
	}
}
