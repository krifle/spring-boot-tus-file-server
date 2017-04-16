package io.tus.wndflwr.model;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class TusResponse {

	private boolean success = true;
	private List<TusHeader> headers = new ArrayList<>();
	private HttpStatus httpStatus = HttpStatus.NO_CONTENT;
	private String message = "success";

	private TusResponse(boolean success, HttpStatus httpStatus) {
		this.success = success;
		this.httpStatus = httpStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public List<TusHeader> getHeaders() {
		return headers;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public static TusResponse success(HttpStatus httpStatus, List<TusHeader> headers) {
		TusResponse response = new TusResponse(true, httpStatus);
		response.headers = headers;
		return response;
	}

	public static TusResponse fail(HttpStatus httpStatus, String message) {
		TusResponse response = new TusResponse(false, httpStatus);
		response.message = message;
		return response;
	}
}
