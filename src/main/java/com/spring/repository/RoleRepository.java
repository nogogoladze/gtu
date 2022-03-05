package com.spring.repository;

import com.spring.model.system.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findUserRoleByName(String roleName);
}
