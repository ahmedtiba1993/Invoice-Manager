package com.tiba.invoice.repository;

import com.tiba.invoice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}