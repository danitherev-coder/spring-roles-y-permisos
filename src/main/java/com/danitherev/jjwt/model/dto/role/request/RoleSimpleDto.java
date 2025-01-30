package com.danitherev.jjwt.model.dto.role.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleDto {
    private Long id;
    @NotEmpty(message = "El nombre del Role es obligatorio")
    private String name;
}
