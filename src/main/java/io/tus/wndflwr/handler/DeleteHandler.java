package io.tus.wndflwr.handler;

import io.tus.wndflwr.config.TusProperties;
import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.file.model.FileInfo;
import io.tus.wndflwr.file.repository.FileRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.tus.wndflwr.constant.HeaderKey.TUS_RESUMABLE;

/**
 * When receiving a `DELETE` request for an existing upload the Server SHOULD free associated resources and MUST
 * respond with the `204 No Content` status confirming that the upload was terminated.
 */
@Service
public class DeleteHandler implements TusHandler {

	@Autowired
	private TusProperties properties;
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

		TusHeader resumable = new TusHeader(TUS_RESUMABLE, properties.getResumableVersion());
		return TusResponse.success(HttpStatus.NO_CONTENT, TusHeader.asList(resumable));
	}
}
