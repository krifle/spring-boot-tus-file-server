package io.tus.wndflwr.controller;

import io.tus.wndflwr.controller.request.model.UserRequest;
import io.tus.wndflwr.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class RegisterController {

	@Autowired
	private UserRegisterService userRegisterService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView getRegister(Principal principal) {
		if (principal != null) {
			return new ModelAndView("redirect:/my");
		}
		return new ModelAndView("account/register");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView postRegister(UserRequest userRequest) {
		userRegisterService.insertUserRegisterRequest(userRequest);

		return new ModelAndView("account/register_success");
	}
}
