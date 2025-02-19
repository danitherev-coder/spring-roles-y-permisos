package com.danitherev.jjwt.services;

import com.danitherev.jjwt.model.dto.auth.request.AuthDto;
import com.danitherev.jjwt.model.dto.auth.request.RegisterDto;
import com.danitherev.jjwt.model.dto.auth.response.AuthResponse;
import com.danitherev.jjwt.model.dto.auth.response.RegisterResponse;
import com.danitherev.jjwt.model.dto.email.request.EmailConfirmationDto;
import com.danitherev.jjwt.model.dto.email.response.EmailResponse;
import com.danitherev.jjwt.model.dto.user.request.ChangePasswordDto;

public interface AuthService {
    AuthResponse login(AuthDto authDto);
    RegisterResponse register(RegisterDto registerDto);
    EmailResponse confirmToken(String token);
    EmailResponse resendEmailConfirmation(EmailConfirmationDto emailConfirmation);
    EmailResponse forgotPassword(EmailConfirmationDto emailConfirmation);
    EmailResponse changePassword(ChangePasswordDto changePasswordDto, String token);
}
