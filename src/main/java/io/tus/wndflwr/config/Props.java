package io.tus.wndflwr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Props {

	@Value("${prop.authorities}")
	private String[] authorities;

	public List<String> getAuthorities() {
		return Arrays.asList(authorities);
	}
}
