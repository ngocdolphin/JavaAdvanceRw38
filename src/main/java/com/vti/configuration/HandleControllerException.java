package com.vti.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vti.exception.ErrorResponse;

@RestControllerAdvice
public class HandleControllerException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		
		ErrorResponse errRp = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Username not found");
		
		return new ResponseEntity<>(errRp, HttpStatus.UNAUTHORIZED);
		//return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errRp);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		
		ErrorResponse errRp = new ErrorResponse(HttpStatus.BAD_REQUEST, "Unknow error");
		
		return new ResponseEntity<>(errRp, HttpStatus.BAD_REQUEST);
		//return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errRp);
	}
	

}
