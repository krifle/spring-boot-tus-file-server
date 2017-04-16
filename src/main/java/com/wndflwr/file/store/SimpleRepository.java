package com.wndflwr.file.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wndflwr.config.TusProperties;
import com.wndflwr.exception.InternalServerException;
import com.wndflwr.exception.NotFoundException;
import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import com.wndflwr.model.request.PatchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@Service
public class SimpleRepository implements Repository {

	@Autowired
	private TusProperties properties;
	@Autowired
	private HttpServletRequest request;

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRepository.class);

	@PostConstruct
	public void makeBaseDirectories() {
		new File(properties.getInfoFilePath()).mkdirs();
		new File(properties.getBinFilePath()).mkdirs();
	}

	@Override
	public FileInfo getFileInfo(String fileId) throws TusException {
		File infoFile = new File(getInfoFilePath(fileId));
		File binFile = new File(getBinFilePath(fileId));

		if (!infoFile.exists() || !binFile.exists()) {
			throw new NotFoundException(fileId);
		}
		FileInfo fileInfo = readFileToValue(infoFile);
		fileInfo.setOffset(binFile.length());
		return fileInfo;
	}

	@Override
	public long write(PatchRequest request, long maxBuffer) throws TusException {
		String filePath = getBinFilePath(request.getFileId());
		File file = new File(filePath);

		if (!file.exists()) { // TODO 500 internal error code?
			throw new NotFoundException("File not found: " + filePath); // TODO type of exception? Should this exception be ignored?
		}

		if (!file.canRead() || !file.canWrite() || !file.isFile()) {
			throw new NotFoundException("File permission error: " + filePath); // TODO type of excption?
		}

		return randomAccessFileWrite(filePath, request.getUploadOffset(), maxBuffer);
	}

	@Override
	public void finish(String fileId) throws TusException {
		// TODO delete temporary file? mv file? .bin to .mp4? etc.
	}

	private String getInfoFilePath(String fileId) {
		return properties.getInfoFilePath() + File.separator + fileId + ".info";
	}

	private String getBinFilePath(String fileId) {
		return properties.getBinFilePath() + File.separator + fileId + ".bin";
	}

	private FileInfo readFileToValue(File infoFile) throws NotFoundException {
		try {
			return new ObjectMapper().readValue(infoFile, FileInfo.class);
		} catch (IOException e) {
			throw new NotFoundException("Cannot read File [" + infoFile.getName() + "] into FileInfo.class");
		}
	}

	private long randomAccessFileWrite(String filePath, long offset, long max) throws TusException {
		try (
				RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
				FileChannel destFileChannel = randomAccessFile.getChannel()
		) {
			if (properties.getMaxSize() > 0L && properties.getMaxSize() < max) {
				max = properties.getMaxSize(); // FIXME!!!
			}

			ReadableByteChannel readableByteChannel = Channels.newChannel(request.getInputStream());

			return destFileChannel.transferFrom(readableByteChannel, offset, max);
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
			throw new InternalServerException("Unknown internal server exception: " + e.getMessage());
		}
	}
}
