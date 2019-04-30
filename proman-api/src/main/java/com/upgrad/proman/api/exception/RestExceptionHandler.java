package com.upgrad.proman.api.exception;

import com.upgrad.proman.api.model.ErrorResponse;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//Thus in case of exceptions, its the exceptionHandler that sends back the response for rest api to convert it to JSON
@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> resourceNotFoundException(
      ResourceNotFoundException exc, WebRequest request) {
    // populate response entity via error-response and error-response via custom exception object.
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AuthenticationFailedException.class)
  public ResponseEntity<ErrorResponse> authenticationFailed(AuthenticationFailedException a, WebRequest request){
    ErrorResponse errorResponse=new ErrorResponse();
    errorResponse.code(a.getCode());
    errorResponse.setMessage(a.getMessage());
    return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.UNAUTHORIZED);

  }
}
