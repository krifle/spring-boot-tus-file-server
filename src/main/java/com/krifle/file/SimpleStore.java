package com.krifle.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krifle.config.TusProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

@Service
public class SimpleStore implements Store {

	@Autowired
	private TusProperties properties;

	@PostConstruct
	public void postConstruct() {
		System.out.println("!!!!!!!!!!!");
		boolean result = new File(properties.getBaseFilePath()).mkdirs();
		System.out.println(result);
	}

	@Override
	public void init() {

	}

	public void saveTest() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setLength(10000L);
		fileInfo.setMeatdata("metadata");

		String filePath = "./myJanghoTest.txt";

		File file = new File(filePath.replaceAll("/", Matcher.quoteReplacement(File.separator)));

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(file, fileInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class FileInfo {

		private String meatdata;
		private long length;

		public String getMeatdata() {
			return meatdata;
		}

		public void setMeatdata(String meatdata) {
			this.meatdata = meatdata;
		}

		public long getLength() {
			return length;
		}

		public void setLength(long length) {
			this.length = length;
		}
	}
}
