package com.cts.exception;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.TradingProjectApplication;
import com.cts.dto.ErrorResponse;
import com.cts.repository.TradingRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorHandler {

	private final TradingRepository tr ;
	private final TradingProjectApplication ts;
	public ErrorHandler(TradingRepository tr, TradingProjectApplication ts) {
		this.tr = tr;
		this.ts = ts;
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex)
	{
		ErrorResponse response=new ErrorResponse();
		response.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(TradeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTradeNotFoundException(TradeNotFoundException ex)
	{
		ErrorResponse response=new ErrorResponse();
		response.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		StringBuilder sb=new StringBuilder();
		for(FieldError fe : ex.getBindingResult().getFieldErrors())
		{
			sb.append(fe.getField()+" : "+fe.getDefaultMessage()+" ");
			
		}
		ErrorResponse response=new ErrorResponse(sb.toString());
		return ResponseEntity.badRequest().body(response);
	}
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String errorMessage = violations.stream()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .collect(Collectors.joining(" "));
 
        ErrorResponse resp = new ErrorResponse();
        resp.setMessage(errorMessage);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
 
}
