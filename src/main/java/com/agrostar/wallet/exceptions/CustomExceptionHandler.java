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
    ErrorResponse error = new ErrorResponse("Server Error", ex.getMessage());
    return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(WalletNotFoundException.class)
  public final ResponseEntity<Object> handleWalletNotFoundException(
      WalletNotFoundException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    return new ResponseEntity(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TransactionNotFoundException.class)
  public final ResponseEntity<Object> handleTransactionNotFoundException(
      TransactionNotFoundException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    return new ResponseEntity(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TransactionFailedException.class)
  public final ResponseEntity<Object> handleTransactionFailedException(
      TransactionFailedException ex, WebRequest request) {
    ErrorResponse error =
        new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }
}
