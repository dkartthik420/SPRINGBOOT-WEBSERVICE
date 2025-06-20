package com.cts.security;

import org.springframework.security.core.Authentication;

import java.nio.charset.MalformedInputException;
import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cts.service.CustomerServiceException;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	
	@Value("${app.jwt.expiry-millies}")
	private long expiryMillis;
	
	
	public String generateToken(Authentication authentication) {
		String username=authentication.getName();
		
		Date currenDate=new Date();
		
		Date expiryDate=new Date(currenDate.getTime()+expiryMillis);
		
		String token=
				Jwts.builder()
				.subject(username)
				.issuedAt(currenDate)
				.expiration(expiryDate)
				.signWith(key())
				.compact();
		
		return token;
	}
	
	
	
	private Key key()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	
	public String getUsername(String token)
	{
		return Jwts.parser().verifyWith((SecretKey) key())
		.build().parseSignedClaims(token)
		.getPayload().getSubject();
	}
	
	
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key())
			.build().parse(token);
			
			return true;
		} catch (MalformedJwtException | SignatureException ex) {
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST,"Invalid token");
		}
		catch (ExpiredJwtException ex) {
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST,"Expired Token");
		}
		catch (UnsupportedJwtException ex) {
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST,"unsupported Token");
		}
		catch (IllegalArgumentException ex) {
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST,"string is null or empty");

		}
	}
	
}
