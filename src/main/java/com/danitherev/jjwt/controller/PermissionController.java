package com.danitherev.jjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danitherev.jjwt.model.dto.permission.request.PermissionDto;
import com.danitherev.jjwt.model.dto.permission.response.PermissionResponse;
import com.danitherev.jjwt.model.dto.permission.response.PermissionSimpleResponse;
import com.danitherev.jjwt.services.PermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PermissionSimpleResponse> create(@RequestBody @Valid PermissionDto permissionDto) {
        return new ResponseEntity<>(permissionService.create(permissionDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return new ResponseEntity<>(permissionService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")    
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(permissionService.findById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionSimpleResponse> update(@PathVariable Long id, @RequestBody @Valid PermissionDto permissionDto) {
        return new ResponseEntity<>(permissionService.update(id, permissionDto), HttpStatus.OK);
    }
}
