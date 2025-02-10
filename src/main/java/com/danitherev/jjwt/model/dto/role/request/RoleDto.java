package com.danitherev.jjwt.model.dto.role.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;


import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;


public class RoleDto {
    private Long id;
    @NotEmpty(message = "El nombre del Role es obligatorio")
    private String name;
    private Set<PermissionSimpleResponse> permissions = new HashSet<>();


    public RoleDto() {
    }

    public RoleDto(Long id, String name, Set<PermissionSimpleResponse> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
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

    public Set<PermissionSimpleResponse> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionSimpleResponse> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
