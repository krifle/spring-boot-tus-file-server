package com.wndflwr.handler;

import com.wndflwr.exception.InvalidContentType;
import com.wndflwr.exception.InvalidOffset;
import com.wndflwr.exception.TusException;
import com.wndflwr.model.TusResponse;
import org.springframework.util.StringUtils;

public class PatchHandler implements TusHandler {

	/**
	 * The Server SHOULD accept `PATCH` requests against any upload URL and apply the bytes contained in the message at
	 * the given offset specified by the `Upload-Offset` header.
	 * All `PATCH` requests MUST use `Content-Type: application/offset+octet-stream`.
	 */
	@Override
	public TusResponse handle(Object... arguments) throws TusException {
		String fileId = (String) arguments[0]; // FIXME must be improved
		String contentType = (String) arguments[1];
		String uploadOffset = (String) arguments[2];

		if (StringUtils.isEmpty(contentType) || !contentType.equals("application/offset+octet-stream")) {
			throw new InvalidContentType("Invalid content type [" + contentType + "]");
		}

		if (StringUtils.isEmpty(uploadOffset) || Long.parseLong(uploadOffset) < 0) {
			throw new InvalidOffset(uploadOffset);
		}



		return null;
	}
}
