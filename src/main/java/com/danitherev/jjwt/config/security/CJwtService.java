package com.danitherev.jjwt.config.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import com.danitherev.jjwt.exceptions.ApiErrors;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.danitherev.jjwt.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class CJwtService {
    @Value("${security.jwt.expiration-minutes}")
    private Long expirationMinutes;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    // Configuracion de la duracion del token para activar cuenta
    @Value("${security.jwt.expiration-minutes-token}")
    private Long expirationMinutesToken;

    public String generateToken(User user, Map<String, Object> extractClaim) {
        Date issuedtAt = new Date();
        Date expiration = new Date(issuedtAt.getTime() + (expirationMinutes * 60 * 1000));

        return Jwts.builder()
        .setClaims(extractClaim)
        .setSubject(user.getUsername())
        .setIssuedAt(issuedtAt)
        .setExpiration(expiration)
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .signWith(generateKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String activationAcc(User user){
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + (expirationMinutesToken * 60 * 1000));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateKey(){
        byte[] secretAsByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsByte);
    }

    public String extractUsername(String jwt){
        return extractAllClaims(jwt).getSubject();
    }

    public String extractEmail(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
        .parseClaimsJws(jwt).getBody();
    }

//    public boolean isTokenValid(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(generateKey())
//                    .build()
//                    .parseClaimsJws(token);
//            return true; // Retorna true si el token es válido
//        } catch (ExpiredJwtException ex) {
//            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El token ha expirado");
//        } catch (UnsupportedJwtException | MalformedJwtException ex) {
//            throw new ApiErrors(HttpStatus.UNAUTHORIZED, "Token inválido: " + ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            throw new ApiErrors(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el argumento del token: " + ex.getMessage());
//        }
//    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // Retorna true si el token es válido
        } catch (ExpiredJwtException ex) {
            return false; // Token expirado
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            return false; // Token inválido
        }
    }


}
