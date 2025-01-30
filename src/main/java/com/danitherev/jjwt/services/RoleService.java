package com.danitherev.jjwt.services;

import java.util.List;

import com.danitherev.jjwt.model.dto.role.request.RoleSimpleDto;
import com.danitherev.jjwt.model.dto.role.response.RoleResponse;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;

public interface RoleService {
    RoleSimpleResponse create(RoleSimpleDto roleSimpleDto);
    RoleResponse getById(Long id);    
    List<RoleResponse> getAll();
    void delete(Long id);
    RoleSimpleResponse update(Long id, RoleSimpleDto roleSimpleDto);
}
