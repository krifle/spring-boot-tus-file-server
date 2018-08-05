package io.tus.wndflwr.controller.request.model;

import io.tus.wndflwr.repository.model.Ip;

import java.util.Date;
import java.util.List;

public class UserRequest {

	private int userRequestSeq;
	private String username;
	private String email;
	private String password;
	private List<Ip> ips;
	private Date requestDate;
	private Status status;
	private Date modDate;

	public int getUserRequestSeq() {
		return userRequestSeq;
	}

	public void setUserRequestSeq(int userRequestSeq) {
		this.userRequestSeq = userRequestSeq;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Ip> getIps() {
		return ips;
	}

	public void setIps(List<Ip> ips) {
		this.ips = ips;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public enum Status {
		REQUESTED
		, APPROVED
		, DENIED
		, SUSPENDED
	}
}
