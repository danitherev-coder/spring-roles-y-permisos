package com.danitherev.jjwt.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.dto.permission.request.PermissionDto;
import com.danitherev.jjwt.model.dto.permission.response.PermissionResponse;
//import com.danitherev.jjwt.model.dto.permission.response.PermissionResponse;
import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;
import com.danitherev.jjwt.model.dto.role.request.RoleDto;
import com.danitherev.jjwt.model.entity.Permission;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.mapper.permission.PermissionMapper;
import com.danitherev.jjwt.repository.PermissionRepository;
import com.danitherev.jjwt.services.PermissionService;
import com.danitherev.jjwt.validations.PermissionValidation;
import com.danitherev.jjwt.validations.RoleValidation;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionValidation permissionValidation;
    @Autowired
    private RoleValidation roleValidation;

    @Override
    public PermissionSimpleResponse create(PermissionDto permissionDto) {

        permissionValidation.validatePermissionDoesNotExists(permissionDto.getName());

        // Verificar y obtener todos los roles especificados
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : permissionDto.getRoles()) {
            Role role = roleValidation.getExistingRole(roleDto.getName());
            roles.add(role);
        }

        // Crear y configurar el nuevo permiso
        Permission permission = new Permission();
        permission.setName(permissionDto.getName().toUpperCase());
        permission.setRoles(roles);

        // Para cada rol, agregar el permiso a su colección de permisos
        for (Role role : roles) {
            role.getPermissions().add(permission);
        }

        // Guardar el permiso
        Permission savedPermission = permissionRepository.save(permission);

        // Convertir Permission a DTO y luego a Response
        return permissionMapper.convertPermissionSimpleDtoToPermissionSimpleResponse(
            permissionMapper.convertPermissionToPermissionSimpleDto(savedPermission)
        );
    }

    @Override
    @Transactional
    public PermissionSimpleResponse update(Long id, PermissionDto permissionDto) {
        // Verificar que el permiso existe
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Permission not found"));
        // Convertir el nuevo nombre a mayúsculas
        permissionDto.setName(permissionDto.getName().toUpperCase());

        // Si el nombre del permiso ha cambiado, verificar que el nuevo nombre no exista
        if (!permission.getName().equals(permissionDto.getName())) {
            permissionValidation.validatePermissionDoesNotExists(permissionDto.getName());
            permission.setName(permissionDto.getName());
        }

        // Solo actualizar roles si se proporcionan en el DTO
        if (permissionDto.getRoles() != null && !permissionDto.getRoles().isEmpty()) {
            // Mantener los roles existentes y agregar los nuevos
            Set<Role> newRoles = new HashSet<>(permission.getRoles()); // Mantener roles existentes
            
            for (RoleDto roleDto : permissionDto.getRoles()) {
                Role role = roleValidation.getExistingRole(roleDto.getName());
                if (!permission.getRoles().contains(role)) {
                    newRoles.add(role);
                    role.getPermissions().add(permission);
                }
            }
            
            permission.setRoles(newRoles);
        }

        // Guardar los cambios
        Permission updatedPermission = permissionRepository.save(permission);

        return permissionMapper.convertPermissionSimpleDtoToPermissionSimpleResponse(
            permissionMapper.convertPermissionToPermissionSimpleDto(updatedPermission)
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Obtener la entidad Permission
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Permission not found"));
        
        // Limpiar las relaciones con los roles
        for (Role role : permission.getRoles()) {
            role.getPermissions().remove(permission);
        }
        permission.getRoles().clear();
        
        // Eliminar el permiso
        permissionRepository.delete(permission);
    }

    @Override    
    public PermissionResponse findById(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Permission not found"));        
        return permissionMapper.permissionDtoToPermissionResponse(permissionMapper.permissionToPermissionDto(permission));
    }

    @Override
    public List<PermissionResponse> getAll() {    
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.permissionDtosToPermissionResponse(permissionMapper.permissionToPermissionDto(permissions));        
    }
    
}
