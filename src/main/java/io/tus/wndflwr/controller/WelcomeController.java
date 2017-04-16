package io.tus.wndflwr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", "yo");
		return "welcome";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
