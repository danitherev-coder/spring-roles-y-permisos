package com.danitherev.jjwt.validations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleValidation {

    private final RoleRepository roleRepository;

    public void validateRoleDoesNotExists(String name){
        if (roleRepository.existsByNameIgnoreCase(name)) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El rol especificado ya existe: " + name);
        }
    }

    public Role getExistingRole(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("El rol especificado no existe: " + name));        
        return role;
    }
    
}
