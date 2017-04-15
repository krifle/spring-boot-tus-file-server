package com.wndflwr.file.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wndflwr.config.TusProperties;
import com.wndflwr.exception.NotFoundException;
import com.wndflwr.exception.TusException;
import com.wndflwr.file.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class SimpleStore implements Store {

	@Autowired
	private TusProperties properties;

	@PostConstruct
	public void makeBaseDirectories() {
		new File(properties.getInfoFilePath()).mkdirs();
		new File(properties.getBinFilePath()).mkdirs();
	}

	@Override
	public FileInfo getFileInfo(String fileId) throws TusException {
		File infoFile = new File(properties.getInfoFilePath() + File.separator + fileId + ".info");
		File binFile = new File(properties.getBinFilePath() + File.separator + fileId + ".bin");

		if (!infoFile.exists() || !binFile.exists()) {
			throw new NotFoundException(fileId);
		}
		FileInfo fileInfo = readFileToValue(infoFile);
		fileInfo.setOffset(binFile.length());
		return fileInfo;
	}

	private FileInfo readFileToValue(File infoFile) throws NotFoundException {
		try {
			return new ObjectMapper().readValue(infoFile, FileInfo.class);
		} catch (IOException e) {
			throw new NotFoundException("Cannot read File [" + infoFile.getName() + "] into FileInfo.class");
		}
	}
}
