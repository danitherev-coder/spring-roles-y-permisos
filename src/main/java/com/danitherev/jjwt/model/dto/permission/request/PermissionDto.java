package com.danitherev.jjwt.model.dto.permission.request;


import java.util.HashSet;
import java.util.Set;

import com.danitherev.jjwt.model.dto.role.request.RoleDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public class PermissionDto {
    private Long id;    
    @NotBlank(message = "El campo no debe estar en blanco")
    private String name;
    @NotEmpty(message = "Debe asignar el Role al que pertencera este permiso")
    private Set<RoleDto> roles = new HashSet<>();

    public PermissionDto() {
    }

    public PermissionDto(Set<RoleDto> roles, String name, Long id) {
        this.roles = roles;
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "PermissionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
