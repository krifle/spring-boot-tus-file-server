package com.wndflwr.file.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class FileInfoTest {

	private FileInfo sut;

	@Test
	public void uuidTest() {
		System.out.println(UUID.randomUUID().toString().replace("-", "_"));
	}

	@Test
	public void decodeMetadata_shouldDecodeTusProtocolMetadataSuccessfully() {
		// given
		String value1 = Base64.encode("Value1".getBytes());
		String value2 = Base64.encode("Value2".getBytes());
		String value3 = Base64.encode("Value3".getBytes());
		String metadata = String.format("Key1 %s, Key2 %s, Key3 %s", value1, value2, value3);

		// when
		sut = new FileInfo(10L, metadata, "username");

		// then
		Map<String, String> decodedMedata = sut.getMetadataMap();
		assertThat(decodedMedata.get("Key1")).isEqualTo("Value1");
		assertThat(decodedMedata.get("Key2")).isEqualTo("Value2");
		assertThat(decodedMedata.get("Key3")).isEqualTo("Value3");
	}

	@Test(expected = RuntimeException.class)
	public void decodeMetadata_throwsAnExceptionWhenInvalidMetadataForm() {
		// given
		String invalidMetadata = "Key1 Value1, Key2 Value2";

		// when
		sut = new FileInfo(10L, invalidMetadata, "username");

		// then
		fail();
	}
}