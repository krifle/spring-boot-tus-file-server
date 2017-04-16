package com.wndflwr.handler;

import com.wndflwr.exception.TusException;
import com.wndflwr.model.TusResponse;
import com.wndflwr.model.request.TusRequest;

public interface TusHandler {
	TusResponse handle(TusRequest request) throws TusException;
}
