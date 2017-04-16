package com.wndflwr.model.request;

public class DeleteRequest implements TusRequest {

	private String fileId;

	public DeleteRequest(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}
}
