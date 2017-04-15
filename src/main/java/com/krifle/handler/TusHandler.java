package com.krifle.handler;

import com.krifle.model.TusResponse;
import com.sun.istack.internal.Nullable;

public interface TusHandler {
	TusResponse handle(@Nullable String... fileId);
}
