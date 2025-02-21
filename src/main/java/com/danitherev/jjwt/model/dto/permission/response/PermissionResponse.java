package com.danitherev.jjwt.model.dto.permission.response;

import java.util.Set;

import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;


public class PermissionResponse {
    private Long id;
    private String name;
    private Set<RoleSimpleResponse> roles;

    public PermissionResponse() {
    }

    public PermissionResponse(Long id, String name, Set<RoleSimpleResponse> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
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

    public Set<RoleSimpleResponse> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleSimpleResponse> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "PermissionResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
