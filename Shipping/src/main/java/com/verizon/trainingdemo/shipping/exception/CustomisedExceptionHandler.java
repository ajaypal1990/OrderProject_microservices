package com.verizon.trainingdemo.shipping.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomisedExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleException(RecordsNotFoundException exception){
		ExceptionResponse response= new ExceptionResponse(new Date(), exception.getMessage(), HttpStatus.NO_CONTENT.value());		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleException(CustomException exception){
		ExceptionResponse response = new ExceptionResponse(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleException(Exception exception){
		ExceptionResponse response = new ExceptionResponse(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}



}
