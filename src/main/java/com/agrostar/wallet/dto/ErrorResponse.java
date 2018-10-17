package com.agrostar.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class ErrorResponse extends Response {
  public ErrorResponse(String message, String details) {
    super();
    this.message = message;
    this.details = details;
  }

  public ErrorResponse(String message) {
    super();
    this.message = message;
  }

  private String details;
}
