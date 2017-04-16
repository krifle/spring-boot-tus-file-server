package io.tus.wndflwr.handler;

import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.file.model.FileInfo;
import io.tus.wndflwr.file.store.FileRepository;
import io.tus.wndflwr.locker.Lock;
import io.tus.wndflwr.locker.Locker;
import io.tus.wndflwr.model.TusHeader;
import io.tus.wndflwr.model.TusResponse;
import io.tus.wndflwr.model.request.DeleteRequest;
import io.tus.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteHandler implements TusHandler {

	@Autowired
	@Qualifier("simpleLocker")
	private Locker locker;
	@Autowired
	@Qualifier("simpleFileRepository")
	private FileRepository fileRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteHandler.class);

	@Override
	public TusResponse handle(TusRequest request) throws TusException {
		DeleteRequest deleteRequest = (DeleteRequest) request;

		try (Lock lock = locker.getLock(deleteRequest.getFileId())) {
			return whileLocked(deleteRequest);
		}
	}

	private TusResponse whileLocked(DeleteRequest request) throws TusException {
		FileInfo fileInfo = fileRepository.getFileInfo(request.getFileId());

		fileRepository.terminate(fileInfo);

		return new TusResponse(TusHeader.asList(), HttpServletResponse.SC_NO_CONTENT);
	}
}
