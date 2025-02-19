package com.danitherev.jjwt.model.dto.email.request;

public record EmailDto(
        String[] toUser,
        String subject,
        String message
) {
}
