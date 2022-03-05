package com.spring.service;

import com.spring.exception.AppException;
import com.spring.model.Server;

import java.util.List;

public interface ServerService {
    Server saveServer(Server server);

    void delete(int server);

    List<Server> getServers();

    Server updateServer(Integer serverId, Server newServer);

    void relateUser(String username, String servername) throws AppException;

    void removeServer(String username, String servername) throws AppException;
}
