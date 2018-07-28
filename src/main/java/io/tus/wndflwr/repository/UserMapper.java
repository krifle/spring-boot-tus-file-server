package io.tus.wndflwr.repository;

import io.tus.wndflwr.controller.request.model.UserSearch;
import io.tus.wndflwr.repository.model.Authority;
import io.tus.wndflwr.repository.model.Ip;
import io.tus.wndflwr.repository.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

	void createTableUser();

	User selectUserByUsername(String username);

	List<User> selectUsers(UserSearch userSearch);

	void insertUser(User user);

	void updateUser(User user);

	void createTableUserAuthority();

	List<Authority> selectUserAuthorityByUserName(String username);

	void insertUserAuthority(Authority authority);

	void deleteUserAuthorityByUsername(String username);

	void createTableUserIp();

	List<Ip> selectUserIpByUsername(String username);

	void insertUserIp(Ip ip);

	void deleteUserIpByUsername(String username);
}
