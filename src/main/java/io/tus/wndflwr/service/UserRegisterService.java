package io.tus.wndflwr.service;

import io.tus.wndflwr.controller.request.model.UserRequest;
import io.tus.wndflwr.repository.UserRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {

	@Autowired
	private UserRequestMapper userRequestMapper;

	public void insertUserRegisterRequest(UserRequest userRequest) {
		Integer userRequestSeq = userRequestMapper.insertUserRequest(userRequest);
		// TODO if config is set to auto approve mode approve it.
		// TODO return the result
	}
}
