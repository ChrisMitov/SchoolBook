package com.school.book.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

  @ExceptionHandler( CustomException.class )
  public final ResponseEntity<Object> handleAllExceptions( CustomException ex ) {
    CustomExceptionSchema exceptionResponse =
        new CustomExceptionSchema(
            ex.getMessage(), ex.getDetails() );
    return new ResponseEntity<>( exceptionResponse, HttpStatus.BAD_REQUEST );
  }

  @ExceptionHandler( UsernameNotFoundException.class )
  public final ResponseEntity<Object> handleUsernameNotFound( UsernameNotFoundException ex ) {
    CustomExceptionSchema exceptionResponse =
        new CustomExceptionSchema(
            ex.getMessage(), ex.getMessage() );
    return new ResponseEntity<>( exceptionResponse, HttpStatus.BAD_REQUEST );
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {
    CustomExceptionSchema exceptionResponse = new CustomExceptionSchema();
    ex.getBindingResult()
        .getAllErrors().stream()
        .findFirst()
        .map( ObjectError::getDefaultMessage ).ifPresent( exceptionResponse::setMessage );
    return new ResponseEntity<>( exceptionResponse, HttpStatus.BAD_REQUEST );
  }

  @ExceptionHandler( ConstraintViolationException.class )
  public ResponseEntity<Object> handleConstraintViolation( ConstraintViolationException ex ) {
    CustomExceptionSchema exceptionResponse =
        new CustomExceptionSchema("You can't delete - please delete it's relationships first!", ex.getMessage() );
    return new ResponseEntity<>( exceptionResponse, HttpStatus.BAD_REQUEST );
  }
}
