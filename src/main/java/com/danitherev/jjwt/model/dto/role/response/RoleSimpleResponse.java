package com.danitherev.jjwt.model.dto.role.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleResponse {
    private Long id;
    private String name;
}
