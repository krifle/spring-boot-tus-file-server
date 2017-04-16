package com.wndflwr.model.request;

import com.wndflwr.exception.NotFoundException;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class HeadRequest implements TusRequest {

	private String fileId;

	public HeadRequest(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public String getValidFileId() throws NotFoundException {
		if (StringUtils.isEmpty(fileId)) {
			throw new NotFoundException("Empty fileId input");
		}

		if (!Pattern.compile("(\\w+)?").matcher(fileId).matches()) {
			throw new NotFoundException("Invalid fileId: " + fileId);
		}

		return fileId;
	}
}
