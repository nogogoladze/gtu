package com.spring.repository;

import com.spring.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository  extends JpaRepository<Server, Integer> {

    Server findServerByServerName(String servername);

    List<Server> findServerBySystemUserId(Integer id);


    Server findAllById(Integer serverId);
}
