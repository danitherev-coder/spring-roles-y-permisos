package com.danitherev.jjwt.model.dto.auth.request;

import jakarta.validation.constraints.NotBlank;


public class AuthDto {
    @NotBlank(message = "Debe proporcionar el username")
    private String username;
    @NotBlank(message = "Debe proporcionar el password")
    private String password;


    public AuthDto() {
    }

    public AuthDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
