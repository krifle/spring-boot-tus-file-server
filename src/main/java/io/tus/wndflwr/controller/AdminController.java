package io.tus.wndflwr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping("userList")
	public ModelAndView userList() {
		return new ModelAndView("admin/user_list.ftl");
	}
}
