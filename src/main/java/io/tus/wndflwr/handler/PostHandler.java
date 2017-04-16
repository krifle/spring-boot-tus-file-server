package io.tus.wndflwr.handler;

import io.tus.wndflwr.config.TusProperties;
import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.file.model.FileInfo;
import io.tus.wndflwr.file.store.FileRepository;
import io.tus.wndflwr.locker.Lock;
import io.tus.wndflwr.locker.Locker;
import io.tus.wndflwr.model.TusHeader;
import io.tus.wndflwr.model.TusResponse;
import io.tus.wndflwr.model.request.PostRequest;
import io.tus.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static io.tus.wndflwr.constant.HeaderKey.LOCATION;
import static io.tus.wndflwr.constant.HeaderKey.TUS_RESUMABLE;

/**
 * The Client MUST send a POST request against a known upload creation URL to request a new upload resource.
 */
@Service
public class PostHandler implements TusHandler {

	@Autowired
	private TusProperties properties;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	@Qualifier("simpleLocker")
	private Locker locker;
	@Autowired
	@Qualifier("simpleFileRepository")
	private FileRepository fileRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(PostHandler.class);
	private static final String TEMPORARY_USER = "temp user"; // TODO get user with Spring security

	@Override
	public TusResponse handle(TusRequest request) throws TusException {
		PostRequest postRequest = (PostRequest) request;
		return go(postRequest);
	}

	private TusResponse go(PostRequest request) throws TusException {
		request.validateMaxSize(properties.getMaxSize());

		FileInfo fileInfo = new FileInfo(request.getUploadLength(), request.getUploadMetadata(), TEMPORARY_USER);

		try (Lock lock = locker.getLock(fileInfo.getId())) {
			return whileLocked(fileInfo);
		}
	}

	private TusResponse whileLocked(FileInfo fileInfo) throws TusException {
		fileRepository.create(fileInfo);

		String url = request.getRequestURL().toString() + "/" + fileInfo.getId();
		LOGGER.info("File created with fileId {}", fileInfo.getId());

		TusHeader location = new TusHeader(LOCATION, url);
		TusHeader resumable = new TusHeader(TUS_RESUMABLE, properties.getResumableVersion());
		return TusResponse.success(HttpStatus.CREATED, TusHeader.asList(location, resumable));
	}
}
