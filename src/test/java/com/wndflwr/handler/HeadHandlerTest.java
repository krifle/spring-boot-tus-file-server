package com.wndflwr.handler;

import com.wndflwr.exception.NotFoundException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadHandlerTest {

	private HeadHandler sut = new HeadHandler();

	@Test(expected = NotFoundException.class)
	public void getFileId_shouldThrowAnExceptionIfNoParamsArePassed() throws NotFoundException {
		// given
		sut.getValidFileId();
	}

	@Test(expected = NotFoundException.class)
	public void getFileId_shouldThowAnExceptionIfPassedFileIdIsInInvalidForm() throws NotFoundException {
		// given
		sut.getValidFileId("some@Invalid?[]File!Name");
	}

	@Test
	public void getFileId_shoulNotThrowAnExceptionIfPassedFieIdIsInValidForm() throws NotFoundException {
		// given
		String validFileId = "123valid_fileId";

		// when
		String result = sut.getValidFileId(validFileId);

		// then
		assertThat(result).isEqualTo(validFileId);
	}
}
