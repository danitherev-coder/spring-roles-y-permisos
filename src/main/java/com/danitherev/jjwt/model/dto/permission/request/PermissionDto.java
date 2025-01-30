package com.danitherev.jjwt.model.dto.permission.request;


import java.util.HashSet;
import java.util.Set;

import com.danitherev.jjwt.model.dto.role.request.RoleDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private Long id;
    @NotEmpty(message = "El nombre del Permiso es obligatorio")
    private String name;
    @NotEmpty(message = "Debe asignar el Role al que pertencera este permiso")
    private Set<RoleDto> roles = new HashSet<>();
}
