package com.agrostar.wallet.exceptions;

import com.agrostar.wallet.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse("Server Error", "details");
    return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(WalletNotFoundException.class)
  public final ResponseEntity<Object> handleUserNotFoundException(
      WalletNotFoundException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase());
    return new ResponseEntity(error, HttpStatus.NOT_FOUND);
  }
}
