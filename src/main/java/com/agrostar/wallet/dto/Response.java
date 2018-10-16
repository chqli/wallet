package com.agrostar.wallet.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class Response {
  protected String message = HttpStatus.OK.getReasonPhrase();
}
