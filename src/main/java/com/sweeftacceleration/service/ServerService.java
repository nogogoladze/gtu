package com.sweeftacceleration.service;

import com.sweeftacceleration.exception.AppException;
import com.sweeftacceleration.model.Server;

import java.util.List;

public interface ServerService {
    Server saveServer(Server server);

    void delete(int server);

    List<Server> getServers();

    Server updateServer(Integer serverId, Server newServer);

    void relateUser(String username, String servername) throws AppException;

    void removeServer(String username, String servername) throws AppException;
}
