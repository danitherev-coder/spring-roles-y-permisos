package com.danitherev.jjwt.model.dto.auth.response;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;    
    private String email;
    private RoleSimpleResponse role;    
}
