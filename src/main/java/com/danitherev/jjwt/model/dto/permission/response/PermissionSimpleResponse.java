package com.danitherev.jjwt.model.dto.permission.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionSimpleResponse {
    private Long id;
    private String name;
}