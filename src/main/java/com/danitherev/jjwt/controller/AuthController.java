package com.danitherev.jjwt.controller;

import com.danitherev.jjwt.model.dto.email.request.EmailConfirmationDto;
import com.danitherev.jjwt.model.dto.email.response.EmailResponse;
import com.danitherev.jjwt.model.dto.user.request.ChangePasswordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.danitherev.jjwt.model.dto.auth.request.AuthDto;
import com.danitherev.jjwt.model.dto.auth.request.RegisterDto;
import com.danitherev.jjwt.model.dto.auth.response.AuthResponse;
import com.danitherev.jjwt.model.dto.auth.response.RegisterResponse;
import com.danitherev.jjwt.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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

    @PreAuthorize("permitAll()")
    @GetMapping("/confirm-email/{token}")
    public ResponseEntity<EmailResponse> confirmEmail(@PathVariable String token){
        return new ResponseEntity<>(authService.confirmToken(token), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/resend-confirmation-email")
    public ResponseEntity<EmailResponse> resendEmailToken(@RequestBody @Valid EmailConfirmationDto emailConfirmation){
        return new ResponseEntity<>(authService.resendEmailConfirmation(emailConfirmation), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/forgot-password")
    public ResponseEntity<EmailResponse> forgotPassword(@RequestBody @Valid EmailConfirmationDto emailConfirmation){
        return new ResponseEntity<>(authService.forgotPassword(emailConfirmation), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PutMapping("/change-password/{token}")
    public ResponseEntity<EmailResponse> forgotPassword(@RequestBody @Valid ChangePasswordDto changePasswordDto,@PathVariable String token){
        return new ResponseEntity<>(authService.changePassword(changePasswordDto, token), HttpStatus.OK);
    }
}
