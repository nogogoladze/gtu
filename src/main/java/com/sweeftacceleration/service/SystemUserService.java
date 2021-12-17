package com.sweeftacceleration.service;

import com.sweeftacceleration.exception.AppException;
import com.sweeftacceleration.model.system.SystemUser;

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
