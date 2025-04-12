package com.eduardo.analiserede.exception.customizadas;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EnderecoException extends RuntimeException {
    private final HttpStatus status;

    public EnderecoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
