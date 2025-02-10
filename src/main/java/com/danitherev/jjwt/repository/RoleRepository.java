package com.danitherev.jjwt.repository;

import com.danitherev.jjwt.model.entity.Role;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface RoleRepository extends CrudRepository<Role,Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Role> findByNameIgnoreCase(String name);

    @NonNull
    @Override
    List<Role> findAll();
}
