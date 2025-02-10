package com.danitherev.jjwt.exceptions;

import org.springframework.http.HttpStatus;



public class ApiErrors extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus estado;
    private String message;

    public ApiErrors(HttpStatus estado, String message) {
        super();  // Pasar el mensaje al constructor de RuntimeException
        this.estado = estado;
        this.message = message;
    }

    public ApiErrors() {
    }

    public HttpStatus getEstado() {
        return estado;
    }

    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
