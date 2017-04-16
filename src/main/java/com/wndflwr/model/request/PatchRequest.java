package com.wndflwr.model.request;

import com.wndflwr.exception.*;
import com.wndflwr.file.model.FileInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class PatchRequest implements TusRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TusRequest.class);

	private String fileId;
	private String contentType;
	private String uploadOffset;
	private String contentLength;

	public PatchRequest(String fileId, String contentType, String uploadOffset, String contentLength) {
		this.fileId = fileId;
		this.contentType = contentType;
		this.uploadOffset = uploadOffset;
		this.contentLength = contentLength;

		LOGGER.debug(ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE));
	}

	public String getFileId() {
		return fileId;
	}

	public Long getUploadOffset() {
		return Long.parseLong(uploadOffset);
	}

	public Long getContentLength() {
		if (contentLengthNotReadable()) {
			return null;
		}
		return Long.parseLong(contentLength);
	}

	public boolean contentLengthNotReadable() {
		return StringUtils.isEmpty(contentLength) || !NumberUtils.isDigits(contentLength);
	}

	public String getValidFileId() throws NotFoundException { // TODO duplicated code with HeadRequest.java
		if (StringUtils.isEmpty(fileId)) {
			throw new NotFoundException("Empty fileId input");
		}

		if (!Pattern.compile("(\\w+)?").matcher(fileId).matches()) {
			throw new NotFoundException("Invalid fileId: " + fileId);
		}

		return fileId;
	}

	public void validate() throws TusException {
		validateContentType();
		validateUploadOffset();
	}

	private void validateContentType() throws InvalidContentTypeException {
		if (StringUtils.isEmpty(contentType) || !StringUtils.equals(contentType, "application/offset+octet-stream")) {
			throw new InvalidContentTypeException("Invalid content type [" + contentType + "]");
		}
	}

	private void validateUploadOffset() throws InvalidOffsetException {
		if (StringUtils.isEmpty(uploadOffset) || !NumberUtils.isDigits(uploadOffset) || Long.parseLong(uploadOffset) < 0) {
			throw new InvalidOffsetException(uploadOffset);
		}
	}

	public void validateOnFileInfo(FileInfo fileInfo) throws TusException {
		validateUploadOffset(fileInfo);
		validateLength(fileInfo);
	}

	private void validateUploadOffset(FileInfo fileInfo) throws MismatchOffsetException {
		if (getUploadOffset() != fileInfo.getOffset()) {
			throw new MismatchOffsetException("Current file size of " + fileInfo.getOffset() +
					" does not match requested upload-offset of" + getUploadOffset());
		}
	}

	private void validateLength(FileInfo fileInfo) throws SizeExceededException {
		if (getContentLength() != null && getContentLength() + getUploadOffset() > fileInfo.getEntityLength()) {
			throw new SizeExceededException("Content-length + upload-offset exceeds entity-length: " +
					getContentLength() + " + " + getUploadOffset() + " > " + fileInfo.getEntityLength());
		}
	}
}
