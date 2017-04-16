package com.wndflwr.handler;

import com.wndflwr.config.TusProperties;
import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.file.store.Repository;
import com.wndflwr.locker.Lock;
import com.wndflwr.locker.Locker;
import com.wndflwr.model.TusHeader;
import com.wndflwr.model.TusResponse;
import com.wndflwr.model.request.PostRequest;
import com.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wndflwr.constant.HeaderKey.LOCATION;

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
	@Qualifier("simpleRepository")
	private Repository repository;

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
		repository.create(fileInfo);

		String url = request.getRequestURL().toString() + "/" + fileInfo.getId();
		LOGGER.info("File created with fileId {}", fileInfo.getId());

		TusHeader location = new TusHeader(LOCATION, url);
		return new TusResponse(TusHeader.asList(location), HttpServletResponse.SC_CREATED);
	}
}
