package com.danitherev.jjwt.model.dto.email.request;

import org.springframework.web.multipart.MultipartFile;

public record EmailFileDto(
        String[] toUser,
        String subject,
        String message,
        MultipartFile file
) {
}
