package com.danitherev.jjwt.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danitherev.jjwt.model.dto.permission.request.PermissionDto;
import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;
import com.danitherev.jjwt.model.dto.role.request.RoleDto;
import com.danitherev.jjwt.model.entity.Permission;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.mapper.permission.PermissionMapper;
import com.danitherev.jjwt.repository.PermissionRepository;
import com.danitherev.jjwt.validations.PermissionValidation;
import com.danitherev.jjwt.validations.RoleValidation;

@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @Mock
    private PermissionValidation permissionValidation;

    @Mock
    private RoleValidation roleValidation;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    private PermissionDto permissionDto;
    private Role role;
    private Permission permission;
    private PermissionSimpleResponse expectedResponse;

    @BeforeEach
    void setUp() {
        // Preparar RoleDto
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ADMIN");

        // Preparar PermissionDto
        permissionDto = new PermissionDto();
        permissionDto.setName("READ");
        Set<RoleDto> roleDtos = new HashSet<>();
        roleDtos.add(roleDto);
        permissionDto.setRoles(roleDtos);

        // Preparar Role
        role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        role.setPermissions(new HashSet<>());

        // Preparar Permission
        permission = new Permission();
        permission.setId(1L);
        permission.setName("READ");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        permission.setRoles(roles);

        // Preparar Response
        expectedResponse = new PermissionSimpleResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("READ");
    }

    @Test
    void create_WhenValidPermissionDto_ShouldCreatePermission() {
        // Given
        when(roleValidation.getExistingRole(anyString())).thenReturn(role);
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);
        when(permissionMapper.convertPermissionToPermissionSimpleDto(any(Permission.class)))
            .thenReturn(any());
        when(permissionMapper.convertPermissionSimpleDtoToPermissionSimpleResponse(any()))
            .thenReturn(expectedResponse);

        // When
        PermissionSimpleResponse result = permissionService.create(permissionDto);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        verify(permissionValidation).validatePermissionDoesNotExists(permissionDto.getName());
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    void create_WhenPermissionNameIsLowerCase_ShouldConvertToUpperCase() {
        // Given
        permissionDto.setName("read");
        when(roleValidation.getExistingRole(anyString())).thenReturn(role);
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);
        when(permissionMapper.convertPermissionToPermissionSimpleDto(any(Permission.class)))
            .thenReturn(any());
        when(permissionMapper.convertPermissionSimpleDtoToPermissionSimpleResponse(any()))
            .thenReturn(expectedResponse);

        // When
        PermissionSimpleResponse result = permissionService.create(permissionDto);

        // Then
        assertNotNull(result);
        verify(permissionRepository).save(argThat(p -> p.getName().equals("READ")));
    }
}