package com.danitherev.jjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danitherev.jjwt.model.dto.auth.request.AuthDto;
import com.danitherev.jjwt.model.dto.auth.request.RegisterDto;
import com.danitherev.jjwt.model.dto.auth.response.AuthResponse;
import com.danitherev.jjwt.model.dto.auth.response.RegisterResponse;
import com.danitherev.jjwt.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthDto authRequest){
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterDto registerDto){
        return ResponseEntity.ok(authService.register(registerDto));
    }
}
