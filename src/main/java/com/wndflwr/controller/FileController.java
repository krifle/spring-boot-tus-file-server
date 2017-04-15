package com.wndflwr.controller;

import com.wndflwr.config.TusProperties;
import com.wndflwr.exception.TusException;
import com.wndflwr.handler.HeadHandler;
import com.wndflwr.handler.OptionsHandler;
import com.wndflwr.handler.PatchHandler;
import com.wndflwr.model.TusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/files")
public class FileController {

	@Autowired
	private TusProperties tusProperties;
	@Autowired
	private OptionsHandler optionsHandler;
	@Autowired
	private HeadHandler headHandler;
	@Autowired
	private PatchHandler patchHandler;

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
	public TusResponse head(@RequestParam String fileId) throws TusException {
		return headHandler.handle(fileId);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{fileId}")
	@ResponseBody
	public TusResponse patch(@RequestParam String fileId,
							 @RequestHeader("Content-Type") String contentType,
							 @RequestHeader("upload-offset") String uploadOffset) throws TusException {
		return patchHandler.handle(fileId, contentType, uploadOffset);
	}

	@ExceptionHandler(TusException.class)
	public String tusExceptionHandler() { // TODO
		return "";
	}
}
