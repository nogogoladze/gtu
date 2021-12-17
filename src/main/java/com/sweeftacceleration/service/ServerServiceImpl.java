package com.sweeftacceleration.service;

import com.sweeftacceleration.enums.AppErrorCode;
import com.sweeftacceleration.exception.AppException;
import com.sweeftacceleration.model.Server;
import com.sweeftacceleration.model.system.SystemUser;
import com.sweeftacceleration.repository.ServerRepository;
import com.sweeftacceleration.repository.SystemUserRepository;
import com.sweeftacceleration.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Override
    public Server saveServer(Server server) {
        log.info("Saving new server");
        try {
            serverRepository.save(server);
        } catch (NullPointerException e) {
            log.info("null pointer");
        }
        return null;
    }

    @Override
    public void delete(int serverId) {
        log.info("Server deleted");
        Server server = serverRepository.getById(serverId);
        serverRepository.delete(server);
    }

    @Override
    public List<Server> getServers() {
        log.info("Get All Servers ");
        return serverRepository.findAll();
    }

    @Override
    public Server updateServer(Integer serverId, Server newServer) {
        log.info("Server updated");
        Server server = serverRepository.findAllById(serverId);

        server.setId(serverId);
        server.setServerName(newServer.getServerName());
        server.setServerStartDate(newServer.getServerStartDate());
        server.setServerEndDate(newServer.getServerEndDate());
        server.setStatus(newServer.isStatus());
        server.setSystemUser(server.getSystemUser());
        return serverRepository.save(newServer);
    }

    @Override
    public void relateUser(String username, String servername) throws AppException {

        try {
            SystemUser systemUser = systemUserRepository.findSystemUserByUserName(username);
            Server server = serverRepository.findServerByServerName(servername);

            if (server.getSystemUser() == null) {
                server.setSystemUser(systemUser);
                serverRepository.save(server);
            } else log.info("server already have user");

        } catch (Exception e) {
            log.info("user or server not found");
            throw new AppException(AppErrorCode.USER_OR_SERVER_NOT_FOUND);
        }

    }

    @Override
    public void removeServer(String username, String servername) throws AppException {
        try {
            Server server = serverRepository.findServerByServerName(servername);

            server.setSystemUser(null);
            serverRepository.save(server);

        } catch (Exception exception) {
            log.info("UNABLE_FIND_SERVER");
            throw new AppException(AppErrorCode.UNABLE_FIND_SERVER);
        }
    }

    public void autoDisableServer() {
        try {
            List<Server> serverList = serverRepository.findAll();
            for (Server server : serverList) {
                if (server.getServerEndDate().equals(Utils.getCurrentDate())) {
                    server.setStatus(false);
                }
                serverRepository.save(server);
            }
        } catch (Exception e) {
            log.info("Server Under User Is Empty");
        }

    }

}
