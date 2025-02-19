package com.danitherev.jjwt.services;

import com.danitherev.jjwt.model.dto.auth.request.AuthDto;
import com.danitherev.jjwt.model.dto.auth.request.RegisterDto;
import com.danitherev.jjwt.model.dto.auth.response.AuthResponse;
import com.danitherev.jjwt.model.dto.auth.response.RegisterResponse;
import com.danitherev.jjwt.model.dto.email.request.EmailConfirmation;
import com.danitherev.jjwt.model.dto.email.response.EmailResponse;

public interface AuthService {
    AuthResponse login(AuthDto authDto);
    RegisterResponse register(RegisterDto registerDto);
    EmailResponse confirmToken(String token);
    EmailResponse resendEmailConfirmation(EmailConfirmation emailConfirmation);

}
