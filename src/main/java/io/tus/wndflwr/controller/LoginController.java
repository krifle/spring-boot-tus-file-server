package io.tus.wndflwr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("account/login");
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		return new ModelAndView("account/register");
	}
}
