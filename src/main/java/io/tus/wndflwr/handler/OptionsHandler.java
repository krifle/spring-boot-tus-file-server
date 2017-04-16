package io.tus.wndflwr.handler;

import io.tus.wndflwr.config.TusProperties;
import io.tus.wndflwr.constant.HeaderKey;
import io.tus.wndflwr.exception.FileLockedException;
import io.tus.wndflwr.model.TusHeader;
import io.tus.wndflwr.model.TusResponse;
import io.tus.wndflwr.model.request.TusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * An `OPTIONS` request MAY be used to gather information about the Server's current configuration. A successful
 * response indicated by the `204 No Content` or `200 OK` status MUST contain the `Tus-Version` header. It MAY include
 * the `Tus-Extension` and `Tus-Max-Size` headers.
 */
@Service
public class OptionsHandler implements TusHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(OptionsHandler.class);

	@Autowired
	private TusProperties tusProperties;

	@Override
	public TusResponse handle(TusRequest request) throws FileLockedException {
		TusHeader extension = new TusHeader(HeaderKey.TUS_EXTENSION, tusProperties.getExtensions());
		TusHeader maxSize = new TusHeader(HeaderKey.TUS_MAX_SIZE, String.valueOf(tusProperties.getMaxSize()));

		return TusResponse.success(HttpStatus.OK, TusHeader.asList(extension, maxSize));
	}
}
