package com.krifle.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

public class SimpleStoreTest {

	private SimpleStore sut = new SimpleStore();

	@Test
	public void t() {
		sut.saveTest();
	}

	@Test
	public void ttt() throws IOException {
		String filePath = "./tmp/myJanghoTest.txt";
		System.out.println(filePath);
		System.out.println(File.separator);

		System.out.println("Hello/You/There".replaceAll("/", Matcher.quoteReplacement(File.separator)));

		System.out.println(new File(".").getCanonicalPath());
	}
}