package com.demo.lemonaid.demo.Domain;

import lombok.Data;

@Data
public class passwordTemp{
	private String email;
	private String password;
	private String checkDuplicate;

	private String name;
	private String personalId;
	private String gender;
	private String tel;
	private String addr;
	private boolean checkAgree;
	private boolean Auth;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getCheckDuplicate() {
		return checkDuplicate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCheckDuplicate(String checkDuplicate) {
		this.checkDuplicate = checkDuplicate;
	}
}