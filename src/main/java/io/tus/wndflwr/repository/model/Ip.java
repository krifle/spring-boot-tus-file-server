package io.tus.wndflwr.repository.model;

public class Ip {

	private String username;
	private String ip;
	private IpType type;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public IpType getType() {
		return type;
	}

	public void setType(IpType type) {
		this.type = type;
	}
}
