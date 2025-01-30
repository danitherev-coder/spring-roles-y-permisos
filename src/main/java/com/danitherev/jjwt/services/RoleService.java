package com.danitherev.jjwt.services;

import java.util.List;

import com.danitherev.jjwt.model.dto.role.request.RoleDto;
import com.danitherev.jjwt.model.dto.role.request.RoleSimpleDto;
import com.danitherev.jjwt.model.dto.role.response.RoleResponse;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;
import com.danitherev.jjwt.model.entity.Role;

public interface RoleService {
    RoleSimpleResponse create(RoleSimpleDto roleSimpleDto);
    RoleResponse getById(Long id);
    List<Role> getAll();
    void delete(Long id);
    RoleResponse update(Long id, RoleDto roleDto);
}
