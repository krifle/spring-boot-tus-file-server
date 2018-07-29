package io.tus.wndflwr.controller;

import io.tus.wndflwr.config.Props;
import io.tus.wndflwr.controller.request.model.AuthorityList;
import io.tus.wndflwr.controller.request.model.UserSearch;
import io.tus.wndflwr.controller.response.model.CommonResult;
import io.tus.wndflwr.repository.model.User;
import io.tus.wndflwr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private Props props;

	// TODO add security check for admin
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView getUserView(@ModelAttribute UserSearch userSearch) {
		return new ModelAndView("admin/user_list")
				.addObject("userList", userService.getUserList(userSearch));
	}

	@RequestMapping(value = "/user/form/{username}", method = RequestMethod.GET)
	public ModelAndView getUserFormViewByUserName(@PathVariable(required = false) String username) {
		return new ModelAndView("admin/user_form")
				.addObject("edit", StringUtils.isNotEmpty(username))
				.addObject("user", userService.getUser(username, true))
				.addObject("authorities", props.getAuthorities());
	}

	@RequestMapping(value = "/user/form", method = RequestMethod.POST)
	public ModelAndView upsertUser(@ModelAttribute User user, String ipList, AuthorityList authorityList) {
		user.setIps(ipList);
		user.setAuthorities(authorityList);
		userService.upsertUser(user);
		return new ModelAndView("redirect:/admin/user/form/" + user.getUsername());
	}

	@RequestMapping(value = "/user/delete/{username}", method = RequestMethod.GET) // TODO DELETE method support
	public ModelAndView deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return new ModelAndView("redirect:/admin/users");
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
