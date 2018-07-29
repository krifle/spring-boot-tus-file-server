package io.tus.wndflwr.repository.model;

import io.tus.wndflwr.exception.UserManageException;

import java.util.regex.Pattern;

public class Ip {

	private static final String DEFAULT_IP = "127.0.0.1";
	private static final Pattern IPV4_PATTERN = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
	private static final Pattern IPV6_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

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

	public void validate() {
		if (type == IpType.IPV4) {
			if (!IPV4_PATTERN.matcher(ip).matches()) {
				throw new UserManageException("Invalid IPV4 pattern: " + ip);
			}
		} else if (type == IpType.IPV6) {
			if (!IPV6_PATTERN.matcher(ip).matches()) {
				throw new UserManageException("Invalid IPV6 pattern: " + ip);
			}
		} else {
			throw new UserManageException("Invalid Ip Type"); // TODO create another exception object
		}
	}

	public static Ip ofDefault() {
		Ip ip = new Ip();
		ip.setUsername(User.DEFAULT_USERNAME);
		ip.setIp(DEFAULT_IP);
		ip.setType(IpType.IPV4);
		return ip;
	}

	public static Ip ofIpv4(String username, String ip) {
		Ip ipObject = new Ip();
		ipObject.ip = ip;
		ipObject.username = username;
		ipObject.setType(IpType.IPV4);
		return ipObject;
	}
}
