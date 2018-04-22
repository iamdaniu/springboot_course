package com.in28minutes.rest.webservices.restfulwebservices.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
		        request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
		        request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> formatted = ex.getBindingResult().getFieldErrors().stream()
		        .map(this::formatBindingError)
		        .collect(Collectors.toList());
		final ExceptionResponse response = new ExceptionResponse("Validation failed", formatted);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	private String formatBindingError(FieldError e) {
		return String.format("%s: %s.%s - %s", e.getDefaultMessage(), e.getObjectName(), e.getField(),
		        e.getRejectedValue());
	}
}
