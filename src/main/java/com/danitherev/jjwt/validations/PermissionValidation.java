package com.danitherev.jjwt.validations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.entity.Permission;
import com.danitherev.jjwt.repository.PermissionRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class PermissionValidation {

    private final PermissionRepository permissionRepository;

    public PermissionValidation(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public void validatePermissionDoesNotExists (String name){
        if (permissionRepository.existsByNameIgnoreCase(name)) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El permiso especificado ya existe");
        }
    }

    public Permission getExistingRole(String name) {
        return permissionRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("El permiso especificado no existe"));
    }
}
