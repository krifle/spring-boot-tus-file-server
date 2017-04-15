package com.wndflwr.handler;

import com.google.common.collect.Lists;
import com.wndflwr.exception.NotFoundException;
import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.file.store.Store;
import com.wndflwr.locker.Lock;
import com.wndflwr.locker.Locker;
import com.wndflwr.model.TusHeader;
import com.wndflwr.model.TusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.regex.Pattern;

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
	private Store store;

	@Override
	public TusResponse handle(@NotNull Object... arguments) throws TusException {
		String fileId = getValidFileId(arguments);

		try (Lock lock = locker.getLock(fileId)) {
			FileInfo fileInfo = store.getFileInfo(fileId);

			List<TusHeader> headerList = Lists.newArrayList();
			if (fileInfo.hasValidMetadata()) {
				headerList.add(new TusHeader(UPLOAD_METADATA, fileInfo.getMetadata()));
			}
			headerList.add(new TusHeader(CACHE_CONTROL, "no-store"));
			headerList.add(new TusHeader(UPLOAD_LENGTH, Long.toString(fileInfo.getEntityLength())));
			headerList.add(new TusHeader(UPLOAD_OFFSET, Long.toString(fileInfo.getOffset())));
			return new TusResponse(headerList, HttpServletResponse.SC_NO_CONTENT);
		}
	}

	String getValidFileId(Object... fileIds) throws NotFoundException {
		try {
			String fileId = (String)fileIds[0]; // FIXME must be improved!

			if (StringUtils.isEmpty(fileId)) {
				throw new NotFoundException("Empty fileId input");
			}

			if (!Pattern.compile("(\\w+)?").matcher(fileId).matches()) {
				throw new NotFoundException("Invalid fileId: " + fileId);
			}

			return fileId;
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			throw new NotFoundException("Invalid fieldIds parameter");
		}
	}
}
