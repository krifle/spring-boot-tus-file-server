package com.wndflwr.file.store;

import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.model.request.PatchRequest;

public interface Repository {

	void create(FileInfo fileInfo) throws TusException;
	FileInfo getFileInfo(String fileId) throws TusException;
	long write(PatchRequest request, long offset) throws TusException;
	void finish(String fileId) throws TusException;
	boolean terminate(FileInfo fileInfo) throws TusException;

}
