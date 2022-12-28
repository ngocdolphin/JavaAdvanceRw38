package com.vti.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vti.exception.ErrorResponse;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		//System.out.println(authException);
		
		ErrorResponse errResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Username or password invalid");
		
		response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(convertObjectToJson(errResponse));
	}
	
	public String convertObjectToJson(Object object) throws JsonProcessingException {
		
		if (object == null) {
			return null;
		}
		
		ObjectMapper obMapper = new ObjectMapper();
		return obMapper.writeValueAsString(object);
	}

}
