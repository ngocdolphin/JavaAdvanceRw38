package com.vti.form;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class AccountSigninForm {
	@NotEmpty(message = "Username can not be empty")
	private String username;
	
	@Length(min = 6, max = 12, message = "Password must has between 6, 12 characters")
	private String password;
}
