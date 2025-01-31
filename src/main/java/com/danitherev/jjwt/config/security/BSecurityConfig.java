package com.danitherev.jjwt.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class BSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final EJwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizeHttp -> {
                authorizeHttp.requestMatchers("/api/v1/auth/**").permitAll();
                authorizeHttp.requestMatchers("/api/v1/users/**").hasRole("ADMIN");                 
                authorizeHttp.requestMatchers("/api/v1/roles/**").hasRole("ADMIN");
                authorizeHttp.requestMatchers("/api/v1/permission/**").hasRole("ADMIN");
                authorizeHttp.anyRequest().denyAll();
            });        
        return http.build();
    }
}
