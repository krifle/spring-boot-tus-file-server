package com.krifle.controller;

import com.krifle.config.TusProperties;
import com.krifle.handler.HeadHandler;
import com.krifle.handler.OptionsHandler;
import com.krifle.model.TusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/files")
public class FileController {

	@Autowired
	private TusProperties tusProperties;
	@Autowired
	private OptionsHandler optionsHandler;
	@Autowired
	private HeadHandler headHandler;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		return "tusVersion = " + tusProperties.getVersion();
	}

	@RequestMapping(method = RequestMethod.OPTIONS)
	@ResponseBody
	public TusResponse option() {
		return optionsHandler.handle();
	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/{fileId}")
	@ResponseBody
	public TusResponse head(@RequestParam String fileId) {
		return headHandler.handle(fileId);
	}

	// TODO @ExceptionHandler
}
