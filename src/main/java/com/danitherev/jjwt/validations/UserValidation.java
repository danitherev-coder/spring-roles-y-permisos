package com.danitherev.jjwt.validations;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {
    @Autowired
    private UserRepository userRepository;

    public void validateUserDoesNotExists(String username, String email){
        if (userRepository.existsByUsername(username)) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El nombre de usuario ya está en uso");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El correo electrónico ya está en uso");
        }
    }

    public User existEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ApiErrors(HttpStatus.BAD_REQUEST, "Email no esta registrado")
        );
    }
}
