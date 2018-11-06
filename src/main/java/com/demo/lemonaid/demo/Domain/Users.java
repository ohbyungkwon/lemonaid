package com.demo.lemonaid.demo.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_name", length = 20, nullable = false, unique = true)
	private String userName;

	@Column(name = "password",length = 100, nullable = false)
	private String password;

	//1:수퍼관리자, 2:관리자, 3:사용자
	@Column(name = "user_type",length = 1, nullable = false)
	private String userType;

	@Column(name = "reg_date",nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate = new Date();
}
