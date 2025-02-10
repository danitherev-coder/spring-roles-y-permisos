package com.danitherev.jjwt.model.dto.permission.response;


public class PermissionSimpleResponse {
    private Long id;
    private String name;


    public PermissionSimpleResponse() {
    }

    public PermissionSimpleResponse(Long id, String name) {
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
        return "PermissionSimpleResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}