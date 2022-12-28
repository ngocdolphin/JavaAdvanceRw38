package com.vti.form;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.customvalidation.DepartmentNameNotExists;

import lombok.Data;

@Data
public class DepartmentForm {
	private int id;
	
	@DepartmentNameNotExists
	private String name;
	
	private int totalMember;
	
	@Pattern(regexp = "DEV|TEST|SCRUM_MASTER|PM", message = "The type must be DEV, TEST, SCRUM_MASTER or PM")
	private String type;
	
	@JsonFormat(pattern = "dd-MM-yyy")
	private Date createdAt;
	
	private List<AcDTO> accounts;
	
	@Data
	public static class AcDTO {
		private int id;
		
		private String username;
		
		private String firstName;
		
		private String lastName;
		
		private String email;
		
		private String role;
		
		private String status;
	}
}
