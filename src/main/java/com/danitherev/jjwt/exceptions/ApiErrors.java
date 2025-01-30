package com.danitherev.jjwt.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiErrors extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus estado;
    private String message;

    public ApiErrors(HttpStatus estado, String message) {
        super();  // Pasar el mensaje al constructor de RuntimeException
        this.estado = estado;
        this.message = message;
    }
}
