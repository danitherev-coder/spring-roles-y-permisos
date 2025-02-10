package com.danitherev.jjwt.model.dto.permission.request;

import jakarta.validation.constraints.NotEmpty;


public class PermissionSimpleDto {
    private Long id;    
    @NotEmpty(message = "El campo no debe estar en blanco")
    private String name;


    public PermissionSimpleDto() {
    }

    public PermissionSimpleDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "PermissionSimpleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
