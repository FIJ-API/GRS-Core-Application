package com.grs.api.grs_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ErroServidorException extends RuntimeException {
    public ErroServidorException(String message) {
        super(message);
    }
}
