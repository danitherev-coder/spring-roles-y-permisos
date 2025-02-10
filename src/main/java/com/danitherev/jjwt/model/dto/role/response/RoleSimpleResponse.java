package com.danitherev.jjwt.model.dto.role.response;


public class RoleSimpleResponse {
    private Long id;
    private String name;

    public RoleSimpleResponse() {
    }

    public RoleSimpleResponse(Long id, String name) {
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
        return "RoleSimpleResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
