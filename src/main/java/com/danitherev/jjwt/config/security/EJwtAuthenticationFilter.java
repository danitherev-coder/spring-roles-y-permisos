package com.danitherev.jjwt.config.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EJwtAuthenticationFilter  extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final CJwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            //TODO: 1. Obtener el HEADER que contiene el JWT
            String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

        String jwt = authHeader.split(" ")[1];
        String username = jwtService.extractUsername(jwt);
        User user = userRepository.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            //TODO: 5. Ejecutar el restro de filtros
            filterChain.doFilter(request, response);

            
        } catch (ExpiredJwtException e) {            
            setErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Token expirado");
            
        } catch (SignatureException e) {
            setErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Firma del token inválida");
        } catch (MalformedJwtException e) {
            setErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Token mal formado");
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Token no soportado");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Token vacío o nulo");
        }
    }
    
    private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"message\": \"%s\"}", message));
    }
}
