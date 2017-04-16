package io.tus.wndflwr.handler;

import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.model.TusResponse;
import io.tus.wndflwr.model.request.TusRequest;

public interface TusHandler {
	TusResponse handle(TusRequest request) throws TusException;
}
