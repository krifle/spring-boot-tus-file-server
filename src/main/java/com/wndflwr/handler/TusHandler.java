package com.wndflwr.handler;

import com.sun.istack.internal.Nullable;
import com.wndflwr.exception.TusException;
import com.wndflwr.model.TusResponse;

public interface TusHandler {
	TusResponse handle(@Nullable Object... arguments) throws TusException;
}
