package com.sweeftacceleration.repository;

import com.sweeftacceleration.model.system.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {
    SystemUser findSystemUserByUserName(String username);
    SystemUser findSystemUserByRandomNumber(String number);

    void findServerById(Integer id);
}
