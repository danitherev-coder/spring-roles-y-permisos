package com.danitherev.jjwt.model.dto.email.request;

import jakarta.validation.constraints.NotEmpty;

public record EmailConfirmation(
        @NotEmpty(message = "Debe proporcional el email")
        String email
) {
}
