package io.tus.wndflwr.controller;

import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.handler.*;
import io.tus.wndflwr.model.TusResponse;
import io.tus.wndflwr.model.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/files")
public class FileController {

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

	@RequestMapping(method = RequestMethod.OPTIONS)
	@ResponseBody
	public TusResponse option() throws TusException {
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
	@ResponseBody
	public TusResponse tusExceptionHandler(TusException tusException) {
		return TusResponse.fail(tusException.getHttpStatus(), tusException.getMessage());
	}
}
