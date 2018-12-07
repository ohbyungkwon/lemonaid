package com.demo.lemonaid.demo.dto;

import lombok.Data;

@Data
public class SiginInDto {
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
	private boolean emailCheck;
}