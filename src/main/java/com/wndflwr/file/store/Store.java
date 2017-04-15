package com.wndflwr.file.store;

import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;

public interface Store {

	FileInfo getFileInfo(String fileId) throws TusException;
}
