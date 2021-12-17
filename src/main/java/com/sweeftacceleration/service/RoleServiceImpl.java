package com.sweeftacceleration.service;

import com.sweeftacceleration.model.system.UserRole;
import com.sweeftacceleration.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        log.info("Saving new role ");
        return roleRepository.save(userRole);
    }

    @Override
    public void delete(int id) {
        log.info("role deleted");
        UserRole userRole = roleRepository.getById(id);
        roleRepository.delete(userRole);
    }
}
