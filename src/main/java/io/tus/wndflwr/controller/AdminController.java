package io.tus.wndflwr.controller;

import io.tus.wndflwr.controller.request.model.UserSearch;
import io.tus.wndflwr.controller.response.model.CommonResult;
import io.tus.wndflwr.repository.model.User;
import io.tus.wndflwr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView getUserView() {
		return new ModelAndView("admin/user_view");
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserList(UserSearch userSearch) {
		return userService.getUserList(userSearch);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(String username) {
		return userService.getUser(username, true);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult postUser(User user) {
		return userService.postUser(user);
	}
}
