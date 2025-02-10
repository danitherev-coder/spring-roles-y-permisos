package com.danitherev.jjwt.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class RoleValidation {
    @Autowired
    private RoleRepository roleRepository;

    public void validateRoleDoesNotExists(String name){
        if (roleRepository.existsByNameIgnoreCase(name)) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El rol especificado ya existe: " + name);
        }
    }

    public Role getExistingRole(String name) {
        return roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("El rol especificado no existe: " + name));
    }
    
}
