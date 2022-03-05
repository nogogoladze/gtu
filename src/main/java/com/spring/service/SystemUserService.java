package com.spring.service;

import com.spring.exception.AppException;
import com.spring.model.system.SystemUser;

import java.util.List;

public interface SystemUserService {
    SystemUser saveSystemUser(SystemUser systemUser);

    void roleSetter(String username, String roleName) throws AppException;

    SystemUser getUser(String username);

    List<SystemUser> getUsers();

    boolean findByEmail(String email);

    SystemUser getUserById(int id);

    void delete(int systemUser);

    void findUserByNumber(String number);

    void deactivationUser(int id);

    void activationUser(int id);

    void removeRole(int id);
}
