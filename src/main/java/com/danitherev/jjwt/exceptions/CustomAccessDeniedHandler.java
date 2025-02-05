package com.danitherev.jjwt.exceptions;

import java.io.IOException;
import java.time.LocalDateTime;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Esto sirve para enviar un JSON como respuesta
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Esto formateará la fecha correctamente
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            "Acceso denegado: No tienes los permisos necesarios",
            "uri="+request.getRequestURI()                    
        );
        String jsonResponse = objectMapper.writeValueAsString(errorDetails); // aca lo estoy transformando a JSON
        response.getWriter().write(jsonResponse);
    } 
}
