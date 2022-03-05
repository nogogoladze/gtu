package com.spring.service;

import com.spring.model.system.UserRole;

public interface RoleService {
    UserRole saveUserRole(UserRole userRole);
    void delete(int userRole);
}
