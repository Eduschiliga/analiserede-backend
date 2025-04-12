package com.eduardo.analiserede.exception.customizadas;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenJWTException extends RuntimeException {
  private final HttpStatus status;

  public TokenJWTException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
