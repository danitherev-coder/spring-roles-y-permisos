package com.danitherev.jjwt.exceptions;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestap, String message, String details) {
        super();
        this.timestamp = timestap;
        this.message = message;
        this.details = details;
    }
}
