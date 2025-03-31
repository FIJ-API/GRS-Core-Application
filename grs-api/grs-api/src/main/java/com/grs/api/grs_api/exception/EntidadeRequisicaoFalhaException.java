package com.grs.api.grs_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntidadeRequisicaoFalhaException extends RuntimeException {
    public EntidadeRequisicaoFalhaException(String message) {
        super(message);
    }
}
