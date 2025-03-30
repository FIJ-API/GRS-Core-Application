package com.grs.api.grs_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EntidadeSemRetornoException extends RuntimeException {
    public EntidadeSemRetornoException(String message) {
        super(message);
    }
}
