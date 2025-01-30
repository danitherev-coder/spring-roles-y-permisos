package com.danitherev.jjwt.repository;

import com.danitherev.jjwt.model.entity.Role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Role> findByNameIgnoreCase(String name);

    @Override
    List<Role> findAll();
}
