package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

public class passwordTemp{
	private String password;
	private String checkDuplicate;

	public String getPassword() {
		return password;
	}

	public String getCheckDuplicate() {
		return checkDuplicate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCheckDuplicate(String checkDuplicate) {
		this.checkDuplicate = checkDuplicate;
	}
}