package com.vti.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class AccountDTO {
	private int id;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String role;
	
	private String status;
	
	private String departmentName;
	
	private int departmentId;
}
