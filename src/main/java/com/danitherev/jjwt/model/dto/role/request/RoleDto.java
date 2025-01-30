package com.danitherev.jjwt.model.dto.role.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;
    @NotEmpty(message = "El nombre del Role es obligatorio")
    private String name;
    //private Set<PermissionDto> permissionDtos = new HashSet<>();
    private Set<PermissionSimpleResponse> permissions = new HashSet<>();
}
