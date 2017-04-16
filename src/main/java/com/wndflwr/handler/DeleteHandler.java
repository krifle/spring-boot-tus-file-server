package com.wndflwr.handler;

import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.file.store.Repository;
import com.wndflwr.locker.Lock;
import com.wndflwr.locker.Locker;
import com.wndflwr.model.TusHeader;
import com.wndflwr.model.TusResponse;
import com.wndflwr.model.request.DeleteRequest;
import com.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletResponse;

public class DeleteHandler implements TusHandler {

	@Autowired
	@Qualifier("simpleLocker")
	private Locker locker;
	@Autowired
	@Qualifier("simpleRepository")
	private Repository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteHandler.class);

	@Override
	public TusResponse handle(TusRequest request) throws TusException {
		DeleteRequest deleteRequest = (DeleteRequest) request;

		try (Lock lock = locker.getLock(deleteRequest.getFileId())) {
			return whileLocked(deleteRequest);
		}
	}

	private TusResponse whileLocked(DeleteRequest request) throws TusException {
		FileInfo fileInfo = repository.getFileInfo(request.getFileId());

		repository.terminate(fileInfo);

		return new TusResponse(TusHeader.asList(), HttpServletResponse.SC_NO_CONTENT);
	}
}
