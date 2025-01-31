package com.danitherev.jjwt.model.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    @NotBlank(message = "Debe proporcionar el username")
    private String username;
    @NotBlank(message = "Debe proporcionar el password")
    private String password;
}
