package com.sweeftacceleration.service;

import com.sweeftacceleration.model.system.UserRole;

public interface RoleService {
    UserRole saveUserRole(UserRole userRole);
    void delete(int userRole);
}
