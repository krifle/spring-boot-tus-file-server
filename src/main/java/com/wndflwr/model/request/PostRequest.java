package com.wndflwr.model.request;

import com.wndflwr.exception.InvalidUploadLengthException;
import com.wndflwr.exception.TusException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class PostRequest implements TusRequest {

	private String uploadLength;
	private String uploadMetadata;

	public PostRequest(String uploadLength, String uploadMetadata) {
		this.uploadLength = uploadLength;
		this.uploadMetadata = uploadMetadata;
	}

	public long getUploadLength() throws TusException {
		if (notLongParsable(uploadLength)) {
			throw new InvalidUploadLengthException(uploadLength);
		}
		return Long.parseLong(uploadLength);
	}

	public String getUploadMetadata() {
		return uploadMetadata;
	}

	private boolean notLongParsable(String stringRepresentation) {
		return StringUtils.isEmpty(stringRepresentation) || !NumberUtils.isDigits(stringRepresentation);
	}

	public void validateMaxSize(long maxSize) throws TusException {
		if (maxSize > 0 && getUploadLength() > maxSize) {
			throw new MaxUploadSizeExceededException(getUploadLength());
		}
	}
}
