package io.tus.wndflwr.handler;

import com.google.common.collect.Lists;
import io.tus.wndflwr.constant.HeaderKey;
import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.file.model.FileInfo;
import io.tus.wndflwr.file.store.FileRepository;
import io.tus.wndflwr.locker.Lock;
import io.tus.wndflwr.locker.Locker;
import io.tus.wndflwr.model.TusHeader;
import io.tus.wndflwr.model.TusResponse;
import io.tus.wndflwr.model.request.HeadRequest;
import io.tus.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
	private FileRepository fileRepository;

	@Override
	public TusResponse handle(TusRequest request) throws TusException {
		HeadRequest headRequest = (HeadRequest) request;

		try (Lock lock = locker.getLock(headRequest.getFileId())) {
			return whileLocked(headRequest);
		}
	}

	private TusResponse whileLocked(HeadRequest request) throws TusException {
		FileInfo fileInfo = fileRepository.getFileInfo(request.getFileId());

		List<TusHeader> headerList = Lists.newArrayList();
		if (fileInfo.hasValidMetadata()) {
			headerList.add(new TusHeader(HeaderKey.UPLOAD_METADATA, fileInfo.getMetadata()));
		}
		headerList.add(new TusHeader(HeaderKey.CACHE_CONTROL, "no-fileRepository"));
		headerList.add(new TusHeader(HeaderKey.UPLOAD_LENGTH, Long.toString(fileInfo.getEntityLength())));
		headerList.add(new TusHeader(HeaderKey.UPLOAD_OFFSET, Long.toString(fileInfo.getOffset())));
		return new TusResponse(headerList, HttpServletResponse.SC_NO_CONTENT);
	}
}
