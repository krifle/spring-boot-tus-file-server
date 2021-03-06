package io.tus.wndflwr.controller;

import io.tus.wndflwr.config.TusProperties;
import io.tus.wndflwr.model.TusResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static io.tus.wndflwr.constant.HeaderKey.TUS_VERSION;

@ControllerAdvice
public class FileControllerResponseAdvice implements ResponseBodyAdvice<Object> {

	@Autowired
	private TusProperties tusProperties;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request, ServerHttpResponse response) {
		if (!(body instanceof TusResponse)) {
			return body;
		}

		response.getHeaders().add(TUS_VERSION, tusProperties.getVersion());

		TusResponse tusBody = (TusResponse) body;
		return tusBody.isSuccess() ? setSuccessTusResponse(tusBody, response) : setFailTusResponse(tusBody, response);
	}

	private Object setSuccessTusResponse(TusResponse body, ServerHttpResponse response) {
		body.getHeaders().forEach(tusHeader -> response.getHeaders().add(tusHeader.getKey(), tusHeader.getValue()));
		response.setStatusCode(body.getHttpStatus());
		return StringUtils.EMPTY;
	}

	private Object setFailTusResponse(TusResponse body, ServerHttpResponse response) {
		response.setStatusCode(body.getHttpStatus());
		return body.getMessage();
	}
}
