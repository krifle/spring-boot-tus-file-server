package com.wndflwr.model.request;

import com.wndflwr.exception.NotFoundException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class HeadRequestTest {

	private HeadRequest sut;

	@Test(expected = NotFoundException.class)
	public void getValidFileId_shouldThrowAnExceptionIfPassedFileIdIsInInvalidForm() throws NotFoundException {
		// given
		String invalidFileId = "some@Invalid?[]File!Name";
		sut = new HeadRequest(invalidFileId);

		// when
		sut.getValidFileId();

		// then
		fail("Should throw NotFoundException");
	}

	@Test
	public void getValidFileId_shouldNotThrowAnExceptionIfPassedFieIdIsInValidForm() throws NotFoundException {
		// given
		String validFileId = "123valid_fileId";
		sut = new HeadRequest(validFileId);

		// when
		String result = sut.getValidFileId();

		// then
		assertThat(result).isEqualTo(validFileId);
	}
}