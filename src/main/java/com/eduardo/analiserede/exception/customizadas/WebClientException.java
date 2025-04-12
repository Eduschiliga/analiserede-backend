package com.eduardo.analiserede.exception.customizadas;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebClientException extends RuntimeException {
    private final HttpStatus status;

    public WebClientException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
