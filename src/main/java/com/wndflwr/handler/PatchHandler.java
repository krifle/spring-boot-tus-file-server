package com.wndflwr.handler;

import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.file.store.Repository;
import com.wndflwr.locker.Lock;
import com.wndflwr.locker.Locker;
import com.wndflwr.model.TusHeader;
import com.wndflwr.model.TusResponse;
import com.wndflwr.model.request.PatchRequest;
import com.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import static com.wndflwr.constant.HeaderKey.UPLOAD_OFFSET;

/**
 * The Server SHOULD accept `PATCH` requests against any upload URL and apply the bytes contained in the message at
 * the given offset specified by the `Upload-Offset` header.
 * All `PATCH` requests MUST use `Content-Type: application/offset+octet-stream`.
 */
@Service
public class PatchHandler implements TusHandler {

	@Autowired
	@Qualifier("simpleLocker")
	private Locker locker;
	@Autowired
	@Qualifier("simpleRepository")
	private Repository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(PatchHandler.class);

	@Override
	public TusResponse handle(TusRequest request) throws TusException {
		PatchRequest patchRequest = (PatchRequest) request;

		try (Lock lock = locker.getLock(patchRequest.getFileId())) {
			return whileLocked(patchRequest);
		}
	}

	private TusResponse whileLocked(PatchRequest request) throws TusException {
		request.validate();

		FileInfo fileInfo = repository.getFileInfo(request.getValidFileId());

		if (fileInfo.alreadyHasEntireFile()) {
			return getResponse(fileInfo);
		}

		request.validateOnFileInfo(fileInfo);

		long transferred = repository.write(request, calculateMaxBuffer(request, fileInfo));
		if (fileInfo.transferCompleted(transferred)) {
			LOGGER.info("Upload " + request.getFileId() + " is completed.");
			repository.finish(request.getFileId());
		}
		return getResponse(fileInfo);
	}

	private long calculateMaxBuffer(PatchRequest request, FileInfo fileInfo) {
		if (request.contentLengthNotReadable()) {
			return fileInfo.getEntityLength() - request.getUploadOffset();
		} else {
			return request.getContentLength();
		}
	}

	private TusResponse getResponse(FileInfo fileInfo) {
		TusHeader uploadOffset = new TusHeader(UPLOAD_OFFSET, Long.toString(fileInfo.getOffset()));
		return new TusResponse(TusHeader.asList(uploadOffset), HttpServletResponse.SC_NO_CONTENT);
	}
}
