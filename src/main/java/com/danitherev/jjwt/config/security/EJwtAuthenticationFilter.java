package com.danitherev.jjwt.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.danitherev.jjwt.exceptions.ErrorDetails;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class EJwtAuthenticationFilter  extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final CJwtService jwtService;

    public EJwtAuthenticationFilter(UserRepository userRepository, CJwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        try {            
            String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authHeader.split(" ")[1];

            // Validar el token usando el servicio CJwtService
            if (!jwtService.isTokenValid(jwt)) {
                setErrors(request, response, HttpStatus.UNAUTHORIZED, "Token inválido");
                return;
            }

            String username = jwtService.extractUsername(jwt);
            User user = userRepository.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);

            
        } catch (SignatureException ex) {
            setErrors( request, response, HttpStatus.UNAUTHORIZED, "Token inválido");
        } catch (ExpiredJwtException ex) {
            setErrors(request, response, HttpStatus.UNAUTHORIZED, "Token expirado");
        } catch (MalformedJwtException ex) {
            setErrors(request, response, HttpStatus.UNAUTHORIZED, "Token mal formado");
        } catch (UnsupportedJwtException ex) {
            setErrors(request, response, HttpStatus.UNAUTHORIZED, "Token no soportado");
        } catch (Exception ex) {
            setErrors(request, response, HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor: " + ex.getMessage());
        }
    }


    private void setErrors(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            message,
            "uri="+request.getRequestURI()
        );

        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonResponse = objectMapper.writeValueAsString(errorDetails);
        response.getWriter().write(jsonResponse);
    }
}
