package com.danitherev.jjwt.model.mapper.role;


import com.danitherev.jjwt.model.dto.role.request.RoleDto;
import com.danitherev.jjwt.model.dto.role.request.RoleSimpleDto;
import com.danitherev.jjwt.model.dto.role.response.RoleResponse;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.mapper.permission.PermissionMapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    
    @Mapping(target = "permissions", ignore = true)
    Role convertRoleSimpleDtoToRole(RoleSimpleDto roleSimpleDto);    
    RoleSimpleDto convertRoletoRoleSimpleDto(Role role);
    RoleSimpleResponse convertRoleSimpleDtoToRoleSimpleResponse(RoleSimpleDto roleSimpleDto);

    // @Mapping(target = "permissions", ignore = true)
    // Role convertRoleDtoToRole(RoleDto roleDto);

    // @Mapping(target = "permissions", ignore = true)
    // RoleDto convertRoleToRoleDto(Role role);

    // ============================================

    //@Mapping(source = "permissions", target = "permissionDtos")
    RoleDto convertRoleToRoleDto(Role role);

    //@Mapping(source = "permissions", target = "permissions")
    RoleResponse convertRoleToRoleResponse(Role role);

    List<RoleDto> convertRolesToRoleDtos(List<Role> roles);
    List<RoleResponse> convertRolesToRoleResponses(List<Role> roles);

    // Para el mapeo inverso
    @Mapping(target = "permissions", ignore = true)
    Role convertRoleDtoToRole(RoleDto roleDto);
}
