package com.krifle.handler;

import com.krifle.exception.InvalidFileIdException;
import com.krifle.model.TusHeader;
import com.krifle.model.TusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

import static com.krifle.constant.HeaderKey.UPLOAD_OFFSET;

/**
 * A HEAD request is used to determine the offset at which the upload should be continued.
 */
@Service
public class HeadHandler implements TusHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeadHandler.class);

	@Override
	public TusResponse handle(@NotNull String... fileIds) {
		String fileId = fileIds[0];

		TusHeader tusHeader = new TusHeader(UPLOAD_OFFSET, "");
		return new TusResponse(TusHeader.asList(tusHeader), HttpServletResponse.SC_OK); // TODO temporary return value
	}

	String getValidFileId(String... fileIds) {
		try {
			String fileId = fileIds[0];

			if (StringUtils.isEmpty(fileId)) {
				throw new InvalidFileIdException();
			}

			if (!Pattern.compile("(\\w+)?").matcher(fileId).matches()) {
				throw new InvalidFileIdException();
			}

			return fileId;
		} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
			throw new InvalidFileIdException();
		}
	}
}
