package com.danitherev.jjwt.model.dto.auth.response;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;


public class RegisterResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;    
    private String email;
    private RoleSimpleResponse role;


    public RegisterResponse() {
    }

    public RegisterResponse(Long id, String username, String firstName, String lastName, String email, RoleSimpleResponse role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }


    @Override
    public String toString() {
        return "RegisterResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleSimpleResponse getRole() {
        return role;
    }

    public void setRole(RoleSimpleResponse role) {
        this.role = role;
    }
}
