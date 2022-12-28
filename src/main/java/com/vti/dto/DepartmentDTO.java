package com.vti.dto;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DepartmentDTO extends RepresentationModel<DepartmentDTO>{
	private int id;
	
	private String name;
	
	private int totalMember;
	
	private String type;
	
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date createdAt;
	
	private List<AcDTO> accounts;
	
	@Data
	public static class AcDTO extends RepresentationModel<DepartmentDTO>{
		private int id;
		
		private String username;
		
		private String firstName;
		
		private String lastName;
		
		private String email;
		
		private String role;
		
		private String status;
	}
}
