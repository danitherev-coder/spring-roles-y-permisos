package com.danitherev.jjwt.exceptions;

import org.springframework.http.HttpStatus;



public class ApiErrors extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final HttpStatus estado;
    private final String message;

    public ApiErrors(HttpStatus estado, String message) {
        super(message);
        this.estado = estado;
        this.message = message;
    }

    public HttpStatus getEstado() {
        return estado;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
