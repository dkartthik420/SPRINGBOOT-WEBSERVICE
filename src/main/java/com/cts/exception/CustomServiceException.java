package com.cts.exception;

import java.net.http.HttpClient;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class CustomServiceException extends RuntimeException {
	private HttpStatus status;
	private String message;
}
