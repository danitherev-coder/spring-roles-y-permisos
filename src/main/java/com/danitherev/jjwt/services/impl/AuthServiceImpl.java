package com.danitherev.jjwt.services.impl;

import java.util.HashMap;
import java.util.Map;

import com.danitherev.jjwt.model.dto.email.request.EmailConfirmationDto;
import com.danitherev.jjwt.model.dto.email.response.EmailResponse;
import com.danitherev.jjwt.model.dto.user.request.ChangePasswordDto;
import com.danitherev.jjwt.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.danitherev.jjwt.config.security.CJwtService;
import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.dto.auth.request.AuthDto;
import com.danitherev.jjwt.model.dto.auth.request.RegisterDto;
import com.danitherev.jjwt.model.dto.auth.response.AuthResponse;
import com.danitherev.jjwt.model.dto.auth.response.RegisterResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.model.mapper.auth.RegisterMapper;
import com.danitherev.jjwt.model.mapper.role.RoleMapper;
import com.danitherev.jjwt.repository.UserRepository;
import com.danitherev.jjwt.services.AuthService;
import com.danitherev.jjwt.validations.RoleValidation;
import com.danitherev.jjwt.validations.UserValidation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final PasswordEncoder passwordEncoder;
    private final RoleValidation roleValidation;
    private final RoleMapper roleMapper;
    private final RegisterMapper registerMapper;
    private final EmailService emailService;

    @Value("${api.url}")
    private String apiUrl;

    public AuthServiceImpl(UserRepository userRepository, CJwtService jwtService, AuthenticationManager authenticationManager, UserValidation userValidation, PasswordEncoder passwordEncoder, RoleValidation roleValidation, RoleMapper roleMapper, RegisterMapper registerMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userValidation = userValidation;
        this.passwordEncoder = passwordEncoder;
        this.roleValidation = roleValidation;
        this.roleMapper = roleMapper;
        this.registerMapper = registerMapper;
        this.emailService = emailService;
    }

    @Override
    public AuthResponse login(AuthDto authDto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByUsername(authDto.getUsername()).orElseThrow(()-> new ApiErrors(HttpStatus.NOT_FOUND, "User not found"));
        if (Boolean.FALSE.equals(user.getConfirmEmail())){
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "No confirmaste el email");
        }

        String jwt = jwtService.generateToken(user, generateExtractClaim(user));

        return new AuthResponse(jwt);
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterDto registerDto) {
        userValidation.validateUserDoesNotExists(registerDto.getUsername(), registerDto.getEmail());    
        User user = registerMapper.registerDtoToUser(registerDto);
        Role userRoleDefault = roleValidation.getExistingRole("USER");

        user.setRole(userRoleDefault);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User savUser = userRepository.save(user);

        String token = jwtService.activationAcc(savUser);
        String message = "Hola " + savUser.getFirstName() + ", " + "para confirmar su email debe seguir el enlace: " + apiUrl+token;
        emailService.sendActivationAccount(savUser.getEmail(), message);

        RegisterDto request = registerMapper.userToRegisterDto(savUser);
        RegisterResponse response = registerMapper.registerDtoToRegisterResponse(request);
        response.setRole(roleMapper.convertRoleSimpleDtoToRoleSimpleResponse(roleMapper.convertRoletoRoleSimpleDto(userRoleDefault)));
        response.setMessage("User registered");

        // Puedes agregar un mensaje indicando que se enviará el email en segundo plano
        response.setMessage("Registro exitoso. Se ha enviado un email de activación a " + savUser.getEmail() + " (si no lo recibes, puedes solicitar reenvío).");

        return response;
    }

    @Override
    @Transactional
    public EmailResponse confirmToken(String token) {
        // Validar que el token no haya expirado
        jwtService.isTokenValid(token);

        String getEmailWithJwt = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(getEmailWithJwt).orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        user.setConfirmEmail(true);
        userRepository.save(user);
        return new EmailResponse(true, "Email ha sido confirmado");
    }

    @Override
    public EmailResponse resendEmailConfirmation(EmailConfirmationDto emailConfirmation) {
        User user = userValidation.existEmail(emailConfirmation.email());
        String token = jwtService.activationAcc(user);
        String url = apiUrl+token;
        String message = "Para poder activar su cuenta debe dar click en el siguiente enlace: " + url;

        emailService.sendActivationAccount(user.getEmail(), message);


        return new EmailResponse(true, "Se envio un correo de verificacion, por favor revise su email");
    }

    @Override
    public EmailResponse forgotPassword(EmailConfirmationDto emailConfirmation) {
        User user = userValidation.existEmail(emailConfirmation.email());
        String token = jwtService.activationAcc(user);
        String message = "Hola " + user.getFirstName()+ " " + user.getLastName() + " , " + "para cambiar el password siga el enlace: " + apiUrl+token;
        emailService.sendActivationAccount(user.getEmail(), message);

        return new EmailResponse(true, "Se acaba de enviar un email, por favor revise su buzon");
    }

    @Override
    public EmailResponse changePassword(ChangePasswordDto changePasswordDto, String token) {
        String email = jwtService.extractEmail(token);
        User user = userValidation.existEmail(email);
        user.setPassword(passwordEncoder.encode(changePasswordDto.password()));
        userRepository.save(user);

        return new EmailResponse(true, "Se cambio el password, ahora puede iniciar sesion");
    }

    private Map<String, Object> generateExtractClaim(User user){
        Map<String, Object> extractClaims = new HashMap<>();
        extractClaims.put("name", user.getFirstName() + " " + user.getLastName());
        extractClaims.put("role", user.getRole().getName());        
        extractClaims.put("permission", user.getRole().getPermissions());

        return extractClaims;
    }
    
}
