package com.danitherev.jjwt.repository;

import com.danitherev.jjwt.model.entity.Permission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface PermissionRepository extends CrudRepository<Permission,Long> {
    Optional<Permission> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    @NonNull
    @Override
    List<Permission> findAll();
}
