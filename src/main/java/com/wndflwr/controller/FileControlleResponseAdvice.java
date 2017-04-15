package com.wndflwr.controller;

import com.wndflwr.config.TusProperties;
import com.wndflwr.model.TusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

import static com.wndflwr.constant.HeaderKey.TUS_RESUMABLE;
import static com.wndflwr.constant.HeaderKey.TUS_VERSION;

@ControllerAdvice
public class FileControlleResponseAdvice implements ResponseBodyAdvice<Object> {

	@Autowired
	private TusProperties tusProperties;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		Method method = returnType.getMethod();
		return method.getReturnType() == TusResponse.class;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request, ServerHttpResponse response) {
		response.getHeaders().add(TUS_RESUMABLE, tusProperties.getResumableVersion());
		response.getHeaders().add(TUS_VERSION, tusProperties.getVersion());
		return ""; // TODO check if tus client uses body
	}
}
