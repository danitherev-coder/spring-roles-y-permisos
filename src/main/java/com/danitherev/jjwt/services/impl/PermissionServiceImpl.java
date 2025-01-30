package com.danitherev.jjwt.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final PermissionValidation permissionValidation;
    private final RoleValidation roleValidation;

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
        permission.setName(permissionDto.getName());
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
    public PermissionSimpleResponse update(Long id, PermissionDto permissionDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {

        permissionRepository.deleteById(id);
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
