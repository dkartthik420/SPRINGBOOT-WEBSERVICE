package com.cts.controller;
import com.cts.dto.AuthResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cts.service.AuthService;
import com.cts.dto.LoginDto;

 
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
 
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
	   System.out.println(loginDto);
		
		var result=authService.login(loginDto);
		AuthResponse response=new AuthResponse();
		response.setJwtToken(result);
		return ResponseEntity.ok(response);
	}
 
}
 
