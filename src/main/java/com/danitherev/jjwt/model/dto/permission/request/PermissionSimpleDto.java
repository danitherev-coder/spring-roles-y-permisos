package com.danitherev.jjwt.model.dto.permission.request;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionSimpleDto {
    private Long id;    
    @NotEmpty(message = "El campo no debe estar en blanco")
    private String name;
}
