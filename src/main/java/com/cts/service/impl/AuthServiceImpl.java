package com.cts.service.impl;

import com.cts.dto.LoginDto;
import com.cts.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.cts.security.JwtTokenProvider;
import com.cts.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public String login(LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authToken=
		new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
		
	Authentication authentication=authenticationManager.authenticate(authToken);
		
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	String token=jwtTokenProvider.generateToken(authentication);
	
		return token;
	}

}
