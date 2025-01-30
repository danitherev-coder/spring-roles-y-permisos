package com.danitherev.jjwt.model.mapper.permission;

import com.danitherev.jjwt.model.dto.permission.request.PermissionDto;
import com.danitherev.jjwt.model.dto.permission.request.PermissionSimpleDto;
import com.danitherev.jjwt.model.dto.permission.response.PermissionResponse;
import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;
import com.danitherev.jjwt.model.entity.Permission;
import com.danitherev.jjwt.model.mapper.role.RoleMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    // ==================== CREATE - CREAR PERMISSION ===========================    
    @Mapping(target = "roles", ignore = true)
    Permission convertPermisionSimpleDtoToPermission(PermissionSimpleDto permissionDto);
    PermissionSimpleDto convertPermissionToPermissionSimpleDto(Permission permission);
    PermissionSimpleResponse convertPermissionSimpleDtoToPermissionSimpleResponse(PermissionSimpleDto permissionDto);
    // ==================== FindById - Obtener Permission y sus Roles asociados ================
    Permission permissionDtoToPermission(PermissionDto permissionDto);
    PermissionDto permissionToPermissionDto(Permission permission);
    PermissionResponse permissionDtoToPermissionResponse(PermissionDto permissionDto);
    // ==================== GET ALL - OBTENER PERMISSIONS Y SUS ROLES ===========================
    List<PermissionDto> permissionToPermissionDto(List<Permission> permissions);    
    @Mapping(source = "roles", target = "roles", ignore = true)
    List<PermissionResponse> permissionDtosToPermissionResponse(List<PermissionDto> permissionDtos);
}
