package com.krifle.handler;

import com.krifle.config.TusProperties;
import com.krifle.model.TusHeader;
import com.krifle.model.TusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import static com.krifle.constant.HeaderKey.TUS_EXTENSION;
import static com.krifle.constant.HeaderKey.TUS_MAX_SIZE;

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
	public TusResponse handle(@Null String... fileId) {
		TusHeader extension = new TusHeader(TUS_EXTENSION, tusProperties.getExtensions());
		TusHeader maxSize = new TusHeader(TUS_MAX_SIZE, String.valueOf(tusProperties.getMaxSize()));

		return new TusResponse(TusHeader.asList(extension, maxSize), HttpServletResponse.SC_OK);
	}
}
