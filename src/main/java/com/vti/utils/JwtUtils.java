package com.vti.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	@Value("${vti.app.jwtSecretKey}")
	private String JWT_SECRET_KEY;
	
	@Value("${vti.app.jwtExpirationMs}")
	private int JWT_EXPIRATION_MS;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION_MS))
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
			return true;
		}catch (Exception ex) {
			System.out.println("Exception validate token: ");
			System.out.println(ex);
			throw ex;
		}
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}
	
}
