package com.danitherev.jjwt.model.dto.user.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public record UploadImageDto(
    //String name,
    @NotNull(message = "Debe proporcionar el ID del usuario - RECORD")
    Long userId,
    @NotNull(message = "Debe subir una imagen - RECORD")
    MultipartFile file
) {
    
}
