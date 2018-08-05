package io.tus.wndflwr.repository;

import io.tus.wndflwr.controller.request.model.UserRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRequestMapper {

	void createTableUserRequest();

	void createTableUserIpRequest();

	Integer insertUserRequest(UserRequest userRequest);
}
