package io.tus.wndflwr.file.repository;

import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.file.model.FileInfo;
import io.tus.wndflwr.model.request.PatchRequest;

public interface FileRepository {

	void create(FileInfo fileInfo) throws TusException;
	FileInfo getFileInfo(String fileId) throws TusException;
	long write(PatchRequest request, long offset) throws TusException;
	void finish(String fileId) throws TusException;
	boolean terminate(FileInfo fileInfo) throws TusException;

}
