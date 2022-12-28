package com.vti;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {
	
	@NonNull
	private int id;
	
	@NonNull
	private String username;
}
