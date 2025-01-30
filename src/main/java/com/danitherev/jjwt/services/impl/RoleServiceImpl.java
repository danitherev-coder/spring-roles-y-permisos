package com.danitherev.jjwt.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.dto.role.request.RoleDto;
import com.danitherev.jjwt.model.dto.role.request.RoleSimpleDto;
import com.danitherev.jjwt.model.dto.role.response.RoleResponse;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.mapper.role.RoleMapper;
import com.danitherev.jjwt.repository.RoleRepository;
import com.danitherev.jjwt.services.RoleService;
import com.danitherev.jjwt.validations.RoleValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleValidation roleValidation;
    private final RoleMapper roleMapper;

    @Override
    public RoleSimpleResponse create(RoleSimpleDto roleSimpleDto) {

        roleSimpleDto.setName(roleSimpleDto.getName().toUpperCase());

        roleValidation.validateRoleDoesNotExists(roleSimpleDto.getName());

        Role role = roleMapper.convertRoleSimpleDtoToRole(roleSimpleDto);
        Role saveRole = roleRepository.save(role);

        return roleMapper.convertRoleSimpleDtoToRoleSimpleResponse(roleMapper.convertRoletoRoleSimpleDto(saveRole));

    }

    @Override
    public RoleResponse getById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Role not found"));
        return roleMapper.roleDtoToRoleResponse(roleMapper.convertRoleToRoleDto(role));
    }

    @Override
    public List<RoleResponse> getAll() {
        List<Role> roles = roleRepository.findAll();
        // Quiero obtener los roles en consola
        System.out.println(roles.toString() + "tengo algo");

        return roleMapper.listRoleDtoToListRoleResponse(roleMapper.listRoleToListRoleDto(roles));
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Role no encontrado"));
        
        // Limpiar las relaciones con los permisos
        role.getPermissions().clear();
        
        // Eliminar el rol
        roleRepository.delete(role);
    }

    @Override
    public RoleResponse update(Long id, RoleDto roleDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
