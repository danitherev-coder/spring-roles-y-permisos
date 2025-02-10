package com.danitherev.jjwt.model.dto.role.request;

import jakarta.validation.constraints.NotEmpty;


public class RoleSimpleDto {
    private Long id;
    @NotEmpty(message = "El nombre del Role es obligatorio")
    private String name;


    public RoleSimpleDto() {
    }

    public RoleSimpleDto(Long id, String name) {
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
        return "RoleSimpleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
