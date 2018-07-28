package io.tus.wndflwr.controller.request.model;

import io.tus.wndflwr.repository.model.Authority;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorityList {

	private List<String> authorityList;

	public List<String> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<String> authorityList) {
		this.authorityList = authorityList;
	}

	public List<Authority> asAuthorityList(String username) {
		return authorityList
				.stream()
				.map(authority -> Authority.of(username, authority))
				.collect(Collectors.toList());
	}
}
