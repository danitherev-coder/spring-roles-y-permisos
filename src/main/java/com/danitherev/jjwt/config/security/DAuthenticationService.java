package com.danitherev.jjwt.config.security;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.danitherev.jjwt.validations.RoleValidation;
import com.danitherev.jjwt.validations.UserValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DAuthenticationService {
    private final UserRepository userRepository;
    private final CJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final PasswordEncoder passwordEncoder;
    private final RoleValidation roleValidation;
    private final RoleMapper roleMapper;
    private final RegisterMapper registerMapper;


    public RegisterResponse register(RegisterDto registerDto){

        userValidation.validateUserDoesNotExists(registerDto.getUsername(), registerDto.getEmail());    
        User user = registerMapper.registerDtoToUser(registerDto);
        Role userRoleDefault = roleValidation.getExistingRole("USER");

        user.setRole(userRoleDefault);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User savUser = userRepository.save(user);
        
        RegisterDto request = registerMapper.userToRegisterDto(savUser);
        RegisterResponse response = registerMapper.registerDtoToRegisterResponse(request);
        response.setRole(roleMapper.convertRoleSimpleDtoToRoleSimpleResponse(roleMapper.convertRoletoRoleSimpleDto(userRoleDefault)));

        return response;
    }


    public AuthResponse login(AuthDto auth) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByUsername(auth.getUsername()).orElseThrow(()-> new ApiErrors(HttpStatus.NOT_FOUND, "User not found"));
        String jwt = jwtService.generateToken(user, generateExtractClaim(user));

        return new AuthResponse(jwt);
    }


    // ===========================================================================================

    private Map<String, Object> generateExtractClaim(User user){
        Map<String, Object> extractClaims = new HashMap<>();
        extractClaims.put("name", user.getFirstName() + " " + user.getLastName());
        extractClaims.put("role", user.getRole().getName());
        // Obtener los permisos y que no aparezca el role dentro xd
        extractClaims.put("permissions", user.getAuthorities().stream()
            .filter(auth -> !auth.getAuthority().startsWith("ROLE_"))
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));

        return extractClaims;
    }

    /*
        // RESULTADO
            {
                "role": "ADMIN",
                "permissions": [ "CREATE" ],
                "name": "Carlos Aspajo",
                "sub": "dani",
                "iat": 1738295577,
                "exp": 1738297377
            }
         */
}
