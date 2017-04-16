package com.wndflwr.handler;

import com.google.common.collect.Lists;
import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.file.store.Repository;
import com.wndflwr.locker.Lock;
import com.wndflwr.locker.Locker;
import com.wndflwr.model.TusHeader;
import com.wndflwr.model.TusResponse;
import com.wndflwr.model.request.HeadRequest;
import com.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.wndflwr.constant.HeaderKey.*;

/**
 * A HEAD request is used to determine the offset at which the upload should be continued.
 */
@Service
public class HeadHandler implements TusHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeadHandler.class);

	@Autowired
	@Qualifier("simpleLocker")
	private Locker locker;
	@Autowired
	@Qualifier("simpleStore")
	private Repository repository;

	@Override
	public TusResponse handle(TusRequest request) throws TusException {
		HeadRequest headRequest = (HeadRequest) request;
		String fileId = headRequest.getFileId();

		try (Lock lock = locker.getLock(fileId)) {
			FileInfo fileInfo = repository.getFileInfo(fileId);

			List<TusHeader> headerList = Lists.newArrayList();
			if (fileInfo.hasValidMetadata()) {
				headerList.add(new TusHeader(UPLOAD_METADATA, fileInfo.getMetadata()));
			}
			headerList.add(new TusHeader(CACHE_CONTROL, "no-repository"));
			headerList.add(new TusHeader(UPLOAD_LENGTH, Long.toString(fileInfo.getEntityLength())));
			headerList.add(new TusHeader(UPLOAD_OFFSET, Long.toString(fileInfo.getOffset())));
			return new TusResponse(headerList, HttpServletResponse.SC_NO_CONTENT);
		}
	}
}
