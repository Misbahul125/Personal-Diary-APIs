package com.collegegroup.personaldiary.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //used for global exception handling as it checks all controllers present.
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseResourceNotFoundException> resourceNotFoundExceptionHandler(ResourceNotFoundException exception)
	{
		ApiResponseResourceNotFoundException apiResponse = new ApiResponseResourceNotFoundException(false, HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return new ResponseEntity<ApiResponseResourceNotFoundException>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseFieldExceptionMessages> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		
		List<FieldExceptionMessage> messages = new ArrayList<>();
		
		//traversing through single/multiple error(s)
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();//type casting each error object into FiedError class
			messages.add(new FieldExceptionMessage(fieldName, error.getDefaultMessage()));
		});
		
		ApiResponseFieldExceptionMessages apiResponse = new ApiResponseFieldExceptionMessages(false, HttpStatus.NOT_FOUND.value(), messages);
		
		return new ResponseEntity<ApiResponseFieldExceptionMessages>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(CredentialException.class)
//	public ResponseEntity<ApiResponseCredentialException> credentialNotFoundExceptionHandler(CredentialException exception)
//	{
//		ApiResponseCredentialException apiResponse = new ApiResponseCredentialException(false, HttpStatus.BAD_REQUEST.value(), exception.getMessage());
//		return new ResponseEntity<ApiResponseCredentialException>(apiResponse,HttpStatus.BAD_REQUEST);
//	}
}
