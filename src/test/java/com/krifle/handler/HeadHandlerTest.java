package com.krifle.handler;

import com.krifle.exception.InvalidFileIdException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadHandlerTest {

	private HeadHandler sut = new HeadHandler();

	@Test(expected = InvalidFileIdException.class)
	public void getFileId_shouldThrowAnExceptionIfNoParamsArePassed() {
		// given
		sut.getValidFileId();
	}

	@Test(expected = InvalidFileIdException.class)
	public void getFileId_shouldThowAnExceptionIfPassedFileIdIsInInvalidForm() {
		// given
		sut.getValidFileId("some@Invalid?[]File!Name");
	}

	@Test
	public void getFileId_shoulNotThrowAnExceptionIfPassedFieIdIsInValidForm() {
		// given
		String validFileId = "123valid_fileId";

		// when
		String result = sut.getValidFileId(validFileId);

		// then
		assertThat(result).isEqualTo(validFileId);
	}

	@Test
	public void t() {
		sut.getValidFileId(null);
	}
}
