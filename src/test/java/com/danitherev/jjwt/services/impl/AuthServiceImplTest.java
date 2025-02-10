package com.danitherev.jjwt.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.danitherev.jjwt.config.security.CJwtService;
import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.dto.auth.request.AuthDto;
import com.danitherev.jjwt.model.dto.auth.response.AuthResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CJwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthDto authDto;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        authDto = new AuthDto();
        authDto.setUsername("testuser");
        authDto.setPassword("password123");

        role = new Role();
        role.setId(1L);
        role.setName("USER");
        role.setPermissions(new HashSet<>());

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(role);
    }

    @Test
    void login_WhenValidCredentials_ShouldReturnAuthResponse() {
        // Given
        String expectedToken = "jwt.token.here";
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsername(authDto.getUsername()))
                .thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class), any(Map.class)))
                .thenReturn(expectedToken);

        // When
        AuthResponse response = authService.login(authDto);

        // Then
        assertNotNull(response);
        assertEquals(expectedToken, response.getJwt());
    }

    @Test
    void login_WhenUserNotFound_ShouldThrowApiErrors() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsername(authDto.getUsername()))
                .thenReturn(Optional.empty());

        // When/Then
        ApiErrors exception = assertThrows(ApiErrors.class, () -> {
            authService.login(authDto);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getEstado());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void login_WhenAuthenticationFails_ShouldPropagateException() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        // When/Then
        assertThrows(RuntimeException.class, () -> {
            authService.login(authDto);
        });
    }
}