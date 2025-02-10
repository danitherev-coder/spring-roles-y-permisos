package com.danitherev.jjwt.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleValidation roleValidation;
    @Autowired
    private RoleMapper roleMapper;

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
        return roles.stream()
            .map(role -> {
                RoleDto dto = roleMapper.convertRoleToRoleDto(role);
                return roleMapper.roleDtoToRoleResponse(dto);
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "Role no encontrado"));
        
        // Verificar si el rol está siendo usado por usuarios
        if (!role.getUsers().isEmpty()) {
            throw new ApiErrors(HttpStatus.BAD_REQUEST, 
                "No se puede eliminar el rol porque está asignado a uno o más usuarios");
        }

        // Limpiar las relaciones con los permisos
        role.getPermissions().clear();
        
        // Eliminar el rol
        roleRepository.delete(role);
    }

    @Override
    public RoleSimpleResponse update(Long id, RoleSimpleDto roleSimpleDto) {
        Role role = roleRepository.findById(id)
        .orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "El id del Role no existe"));        

        roleValidation.validateRoleDoesNotExists(roleSimpleDto.getName());

        // convertir lo que se va a guardar en DB en mayuscula
        role.setName(roleSimpleDto.getName());

        Role saveRole = roleRepository.save(role);

        return roleMapper.convertRoleSimpleDtoToRoleSimpleResponse(roleMapper.convertRoletoRoleSimpleDto(saveRole));
    }
}
