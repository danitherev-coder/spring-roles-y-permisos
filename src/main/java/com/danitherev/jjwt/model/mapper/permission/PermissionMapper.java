package com.danitherev.jjwt.model.mapper.permission;

import com.danitherev.jjwt.model.dto.permission.request.PermissionDto;
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

    @Mapping(source = "roles", target = "roles")
    Permission convertPermisionDtoToPermission(PermissionDto permissionDto);

    @Mapping(source = "roles", target = "roles")
    PermissionDto convertPermissionToPermissionDto(Permission permission);

    @Mapping(source = "roles", target = "roles")
    //@Mapping(target = "roles", ignore = true)
    PermissionResponse convertPermissionDtoToPermissionResponses(PermissionDto permissionDto);
    
    PermissionSimpleResponse convertPermissionDtoToPermissionResponse(PermissionDto permissionDto);

    List<Permission> convertPermissionDtoListToPermissionList(List<PermissionDto> permissionDtos);
    List<PermissionDto> permissionToPermissionDto(List<Permission> permissions);
    //List<PermissionResponse> permissionDtosToPermissionResponse(List<PermissionDto> permissionDtos);
    @Mapping(source = "roles", target = "roles", ignore = true)
    // List<PermissionSimpleResponse> permissionDtosToPermissionResponse(List<PermissionDto> permissionDtos);
    List<PermissionResponse> permissionDtosToPermissionResponse(List<PermissionDto> permissionDtos);
}
