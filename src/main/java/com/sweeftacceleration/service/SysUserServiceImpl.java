package com.sweeftacceleration.service;

import com.sweeftacceleration.enums.AppErrorCode;
import com.sweeftacceleration.exception.AppException;
import com.sweeftacceleration.model.system.SystemUser;
import com.sweeftacceleration.model.system.UserRole;
import com.sweeftacceleration.repository.RoleRepository;
import com.sweeftacceleration.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional
public class SysUserServiceImpl implements SystemUserService, UserDetailsService {

    private Logger log = Logger.getLogger(String.valueOf(SysUserServiceImpl.class));

    private final SystemUserRepository systemUserRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserRepository.findSystemUserByUserName(username);
        if (systemUser == null) {
            log.info("user not found");
        }

        Collection<SimpleGrantedAuthority> autorities = new ArrayList<>();
        assert systemUser != null;
        systemUser.getUserRole().forEach(userRole -> {
            autorities.add(new SimpleGrantedAuthority(userRole.getName()));
        });

        //TODO გადასაკეთებელია!!!
        ServerServiceImpl serverService = new ServerServiceImpl();
        serverService.autoDisableServer();

        return new User(systemUser.getUserName(), systemUser.getPassword(), systemUser.getActive(), true, true, true, autorities);
    }

    @Override
    public SystemUser saveSystemUser(SystemUser systemUser) {
        log.info("Saving new user");
        return systemUserRepository.save(systemUser);
    }

    @Override
    public void roleSetter(String username, String roleName) throws AppException {
        try {
            SystemUser systemUser = systemUserRepository.findSystemUserByUserName(username);
            UserRole userRole = roleRepository.findUserRoleByName(roleName);

            if(userRole == null) {
                log.info("Role not exist");
            }
            systemUser.getUserRole().add(userRole);

            systemUserRepository.save(systemUser);
        } catch (Exception e) {
            log.info("unable set role");
            throw new AppException(AppErrorCode.ROLE_SETTER_EXCEPTION);
        }

    }

    @Override
    public SystemUser getUser(String username) {
        log.info("Get user by username");
        return systemUserRepository.findSystemUserByUserName(username);
    }

    @Override
    public List<SystemUser> getUsers() {
        log.info("Get All Users ");
        return systemUserRepository.findAll();
    }

    @Override
    public boolean findByEmail(String email) {
        return false;
    }

    @Override
    public SystemUser getUserById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        log.info("user deleted");
        SystemUser user = systemUserRepository.getById(id);
        if (user.getUserRole() != null) {
            user.setUserRole(null);
        }
        systemUserRepository.delete(user);
    }

    @Override
    public void findUserByNumber(String number) {
        SystemUser user = systemUserRepository.findSystemUserByRandomNumber(number);
        if (user != null) {
            log.info("user activated");
            user.setActive(true);
            systemUserRepository.save(user);
        }
    }

    @Override
    public void deactivationUser(int id) {
        SystemUser user = systemUserRepository.getById(id);
        if (user.getActive()) {
            user.setActive(false);
        }
        systemUserRepository.save(user);
    }

    @Override
    public void activationUser(int id) {
        SystemUser user = systemUserRepository.getById(id);
        if (!user.getActive()) {
            user.setActive(true);
        }
        systemUserRepository.save(user);
    }

    @Override
    public void removeRole(int id) {
        SystemUser user = systemUserRepository.getById(id);
        if (user.getUserRole() != null) {
            user.setUserRole(null);
        }
        systemUserRepository.save(user);
    }
}
