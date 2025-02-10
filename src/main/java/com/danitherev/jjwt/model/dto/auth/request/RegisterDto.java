package com.danitherev.jjwt.model.dto.auth.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterDto {
    private Long id;
    @NotEmpty(message = "Username es un campo obligatorio")
    private String username;
    @NotEmpty(message = "FirstName es un campo obligatorio")
    private String firstName;
    @NotEmpty(message = "LastName es un campo obligatorio")
    private String lastName;
    @NotEmpty(message = "Email es un campo obligatorio")
    @Email
    private String email;
    @NotEmpty(message = "Password es un campo obligatorio")
    @Size(min = 6, message = "El password debe tener como minimo 6 caracteres")
    private String password;


    public RegisterDto() {
    }

    public RegisterDto(Long id, String username, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
