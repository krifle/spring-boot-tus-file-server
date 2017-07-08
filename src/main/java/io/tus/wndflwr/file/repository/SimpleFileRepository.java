package io.tus.wndflwr.file.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tus.wndflwr.config.TusProperties;
import io.tus.wndflwr.exception.InternalServerException;
import io.tus.wndflwr.exception.NotFoundException;
import io.tus.wndflwr.exception.TusException;
import io.tus.wndflwr.file.model.FileInfo;
import io.tus.wndflwr.model.request.PatchRequest;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
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
public class SimpleFileRepository implements FileRepository {

	@Autowired
	private TusProperties properties;
	@Autowired
	private HttpServletRequest request;

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFileRepository.class);

	@PostConstruct
	public void makeBaseDirectories() {
		new File(properties.getInfoFilePath()).mkdirs();
		new File(properties.getBinFilePath()).mkdirs();
	}

	@Override
	public void create(FileInfo fileInfo) throws TusException {
		File infoFile = new File(getInfoFilePath(fileInfo.getId())); // TODO file authentication, race condition, lock etc...
		writeFileInfoToFile(fileInfo, infoFile);

		String binFilePath = getBinFilePath(fileInfo.getId());

		File binFile = new File(binFilePath);
		try {
			if (!binFile.createNewFile()) {
				LOGGER.error("File creation failed: ", fileInfo.getId());
				throw new RuntimeException(); // TODO
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public FileInfo getFileInfo(String fileId) throws TusException {
		File infoFile = new File(getInfoFilePath(fileId));
		File binFile = new File(getBinFilePath(fileId));

		if (!infoFile.exists() || !binFile.exists()) {
			throw new NotFoundException(fileId);
		}
		FileInfo fileInfo = readFileInfoFromFile(infoFile);
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
		// TODO delete temporary file? mv file?  change file extension .bin to .mp4? etc.
	}

	@Override
	public boolean terminate(FileInfo fileInfo) throws TusException {
		return new File(getInfoFilePath(fileInfo.getId())).delete() &&
				new File(getBinFilePath(fileInfo.getId())).delete();
	}

	private String getInfoFilePath(String fileId) {
		return properties.getInfoFilePath() + File.separator + fileId + ".info";
	}

	private String getBinFilePath(String fileId) {
		return properties.getBinFilePath() + File.separator + fileId + ".bin";
	}

	private FileInfo readFileInfoFromFile(File infoFile) throws NotFoundException {
		try {
			return new ObjectMapper().readValue(infoFile, FileInfo.class);
		} catch (IOException e) {
			throw new NotFoundException("Cannot read File [" + infoFile.getName() + "] into FileInfo.class");
		}
	}

	private void writeFileInfoToFile(FileInfo fileInfo, File infoFile) {
		try {
			new ObjectMapper().writeValue(infoFile, fileInfo);
		} catch (IOException e) {
			LOGGER.error("IOException: ", e);
			throw new InternalException("Cannot write file[" + infoFile.getName() + "]");
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
