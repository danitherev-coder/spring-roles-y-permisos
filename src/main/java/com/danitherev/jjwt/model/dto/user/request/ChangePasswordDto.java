package com.danitherev.jjwt.model.dto.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ChangePasswordDto(
        @NotEmpty
        @Size(min = 6, message = "El password debe tener como minimo 6 caracteres")
        String password
) {
}
