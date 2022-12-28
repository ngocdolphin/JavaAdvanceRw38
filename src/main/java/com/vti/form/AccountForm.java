package com.vti.form;

import lombok.Data;

@Data
public class AccountForm {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private String status;
	private int departmentId;
}
