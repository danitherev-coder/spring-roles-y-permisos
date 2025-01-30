package com.danitherev.jjwt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danitherev.jjwt.model.dto.role.request.RoleSimpleDto;
import com.danitherev.jjwt.model.dto.role.response.RoleSimpleResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.services.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleSimpleResponse> create(@RequestBody @Valid RoleSimpleDto roleSimpleDto){
        return new ResponseEntity<>(roleService.create(roleSimpleDto), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Role>> getAll(){
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

}
