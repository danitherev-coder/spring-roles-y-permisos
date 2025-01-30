package com.danitherev.jjwt.model.mapper.role;


import com.danitherev.jjwt.model.dto.role.request.RoleDto;
import com.danitherev.jjwt.model.dto.role.request.RoleSimpleDto;
import com.danitherev.jjwt.model.dto.role.response.RoleResponse;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;
import com.danitherev.jjwt.model.entity.Role;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    // =========================== CREATE ==========================
    @Mapping(target = "permissions", ignore = true)
    Role convertRoleSimpleDtoToRole(RoleSimpleDto roleSimpleDto);    
    RoleSimpleDto convertRoletoRoleSimpleDto(Role role);    
    RoleSimpleResponse convertRoleSimpleDtoToRoleSimpleResponse(RoleSimpleDto roleSimpleDto);
    // ============================ FindById - Obtener Role y sus Permisos
    RoleResponse roleDtoToRoleResponse(RoleDto roleDto);
    // ============================ GET ALL - Obtener todos los roles y sus Permisos
    List<RoleDto> listRoleToListRoleDto(List<Role> roles);
    List<RoleResponse> listRoleDtoToListRoleResponse(List<RoleDto> roleDtos);
    // ================== NO LO ESTOY USANDO EN EL RoleServiceImpl - SINO QUE FUNCIONA EN PERMISSIONMAPPER XDDD ========================
    // Sin esto, al hacer un GET en Permission, me devuele un error de que hay una recursion infinita
    RoleDto convertRoleToRoleDto(Role role);    
    @Mapping(target = "permissions", ignore = true)
    Role convertRoleDtoToRole(RoleDto roleDto);
}
