package com.wndflwr.controller;

import com.wndflwr.config.TusProperties;
import com.wndflwr.exception.TusException;
import com.wndflwr.handler.*;
import com.wndflwr.model.TusResponse;
import com.wndflwr.model.request.*;
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
	private PostHandler postHandler;
	@Autowired
	private HeadHandler headHandler;
	@Autowired
	private PatchHandler patchHandler;
	@Autowired
	private DeleteHandler deleteHandler;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		return "tusVersion = " + tusProperties.getVersion();
	}

	@RequestMapping(method = RequestMethod.OPTIONS)
	@ResponseBody
	public TusResponse option() {
		return optionsHandler.handle(new OptionsRequest());
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public TusResponse post(@RequestHeader("upload-length") String uploadLength,
							@RequestHeader("Upload-Metadata") String uploadMetadata) throws TusException {
		TusRequest request = new PostRequest(uploadLength, uploadMetadata);
		return postHandler.handle(request);
	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/{fileId}")
	@ResponseBody
	public TusResponse head(@PathVariable String fileId) throws TusException {
		TusRequest request = new HeadRequest(fileId);
		return headHandler.handle(request);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{fileId}")
	@ResponseBody
	public TusResponse patch(@PathVariable String fileId,
							 @RequestHeader("Content-Type") String contentType,
							 @RequestHeader("upload-offset") String uploadOffset,
							 @RequestHeader("content-length") String contentLength) throws TusException {
		TusRequest request = new PatchRequest(fileId, contentType, uploadOffset, contentLength);
		return patchHandler.handle(request);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{fileId}")
	@ResponseBody
	public TusResponse delete(@PathVariable String fileId) throws TusException {
		TusRequest request = new DeleteRequest(fileId);
		return deleteHandler.handle(request);
	}

	@ExceptionHandler(TusException.class)
	public String tusExceptionHandler() { // TODO
		return "";
	}
}
