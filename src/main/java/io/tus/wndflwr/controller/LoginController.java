package io.tus.wndflwr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin(Principal principal) {
		if (principal != null) {
			return new ModelAndView("redirect:/manager");
		}
		return new ModelAndView("account/login");
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public ModelAndView getMy() {
		return null;
	}
}
