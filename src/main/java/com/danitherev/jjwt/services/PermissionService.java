package com.danitherev.jjwt.services;

import java.util.List;

import com.danitherev.jjwt.model.dto.permission.request.PermissionDto;
import com.danitherev.jjwt.model.dto.permission.response.PermissionResponse;
import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;

public interface PermissionService {
    PermissionSimpleResponse create(PermissionDto permissionDto);
    PermissionSimpleResponse update(Long id, PermissionDto permissionDto);
    void delete(Long id);
    PermissionResponse findById(Long id);
    List<PermissionResponse> getAll();
}
