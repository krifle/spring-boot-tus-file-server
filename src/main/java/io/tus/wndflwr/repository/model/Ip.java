package io.tus.wndflwr.repository.model;

public class Ip {

	private static final String DEFAULT_IP = "127.0.0.1";

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

	public static Ip ofDefault() {
		Ip ip = new Ip();
		ip.setUsername(User.DEFAULT_USERNAME);
		ip.setIp(DEFAULT_IP);
		ip.setType(IpType.IPV4);
		return ip;
	}
}
