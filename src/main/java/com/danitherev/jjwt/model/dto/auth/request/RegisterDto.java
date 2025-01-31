package com.danitherev.jjwt.model.dto.auth.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
