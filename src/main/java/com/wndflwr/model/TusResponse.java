package com.wndflwr.model;

import java.util.ArrayList;
import java.util.List;

public class TusResponse {

	private List<TusHeader> headers = new ArrayList<>();
	private int responseCode;

	public TusResponse(List<TusHeader> headers, int responseCode) {
		this.headers = headers;
		this.responseCode = responseCode;
	}

	public List<TusHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<TusHeader> headers) {
		this.headers = headers;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
