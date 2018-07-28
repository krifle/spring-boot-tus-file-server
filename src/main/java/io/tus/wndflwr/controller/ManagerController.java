package io.tus.wndflwr.controller;

import io.tus.wndflwr.repository.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManagerController {

	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public ModelAndView getTusView() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ModelAndView("/tus/default_view")
				.addObject("hasAdminAuthority", user.hasAdminAuthority());
	}
}
