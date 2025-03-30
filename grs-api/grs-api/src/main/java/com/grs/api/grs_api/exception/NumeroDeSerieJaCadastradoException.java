package com.grs.api.grs_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NumeroDeSerieJaCadastradoException extends RuntimeException {
    public NumeroDeSerieJaCadastradoException(String message) {
        super(message);
    }
}
