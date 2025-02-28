package com.danitherev.jjwt.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.danitherev.jjwt.exceptions.CustomAccessDeniedHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BSecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final EJwtAuthenticationFilter jwtAuthenticationFilter;

    public BSecurityConfig(AuthenticationProvider authenticationProvider, EJwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptions -> exceptions.accessDeniedHandler(customAccessDeniedHandler))
            .authorizeHttpRequests(authorizeHttp -> {
                authorizeHttp.requestMatchers("/api/v1/auth/**").permitAll();
                authorizeHttp.requestMatchers("/api/v1/users/**").permitAll();
                authorizeHttp.requestMatchers("/api/v1/roles/**").hasAnyAuthority("ROLE_ADMIN", "READ");
                authorizeHttp.requestMatchers("/api/v1/permission/**").hasAnyAuthority("ROLE_ADMIN", "READ");
                authorizeHttp.anyRequest().denyAll();
            });        
        return http.build();
    }
}
