package com.danitherev.jjwt.model.dto.role.response;


import java.util.Set;

import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;

    private Set<PermissionSimpleResponse> permissions;
}
