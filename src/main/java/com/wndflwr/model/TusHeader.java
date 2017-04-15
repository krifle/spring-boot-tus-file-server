package com.wndflwr.model;

import com.google.common.collect.Lists;

import java.util.List;

public class TusHeader {

	private String key;
	private String value;

	public TusHeader(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static List<TusHeader> asList(TusHeader... header) {
		return Lists.newArrayList(header);
	}
}
