package com.eduardo.analiserede.exception.handler;

import com.eduardo.analiserede.entity.Endereco;
import com.eduardo.analiserede.exception.customizadas.EnderecoException;
import com.eduardo.analiserede.exception.customizadas.TokenJWTException;
import com.eduardo.analiserede.exception.customizadas.WebClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EnderecoException.class)
  public ResponseEntity<ApiError> handlerEnderecoException(EnderecoException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());

    return new ResponseEntity<>(apiError, ex.getStatus());
  }

  @ExceptionHandler(WebClientException.class)
  public ResponseEntity<ApiError> handlerWebClientException(WebClientException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());

    return new ResponseEntity<>(apiError, ex.getStatus());
  }

  @ExceptionHandler(TokenJWTException.class)
  public ResponseEntity<ApiError> handlerTokenJWTException(TokenJWTException ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());

    return new ResponseEntity<>(apiError, ex.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handlerGenericException(Exception ex, HttpServletRequest request) {
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI());

    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
