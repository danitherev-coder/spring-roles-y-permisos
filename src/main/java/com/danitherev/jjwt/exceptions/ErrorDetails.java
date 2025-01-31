package com.danitherev.jjwt.exceptions;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDetails {    
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime timestap, String message, String details) {
        super();
        this.timestamp = timestap;
        this.message = message;
        this.details = details;
    }
}
