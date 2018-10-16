package com.agrostar.wallet.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

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
