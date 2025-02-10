package com.danitherev.jjwt.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.entity.Permission;
import com.danitherev.jjwt.repository.PermissionRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class PermissionValidation {
    @Autowired
    private PermissionRepository permissionRepository;

    public void validatePermissionDoesNotExists (String name){
        if (permissionRepository.existsByNameIgnoreCase(name)) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, "El permiso especificado ya existe");
        }
    }

    public Permission getExistingRole(String name) {
        Permission permission = permissionRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("El permiso especificado no existe"));        
        return permission;
    }
}
