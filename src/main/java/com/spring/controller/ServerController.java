package com.spring.controller;

import com.spring.model.Server;
import com.spring.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("app/v1/")
public class ServerController {
    private final ServerService serverService;

    @GetMapping("server/servers")
    public List<Server> getAllServer() {
        return serverService.getServers();
    }

    @PostMapping("system/server/save")
    public Server saveServer(@RequestBody Server server) throws Exception{
        return serverService.saveServer(server);
    }

    @PutMapping("system/server/update/{id}")
    public Server updateServer(@PathVariable("id") Integer serverId,@RequestBody Server newServer) {
        return serverService.updateServer(serverId,newServer);

    }

    @DeleteMapping("system/server/delete/{id}")
    public void deleteServer(@PathVariable Integer id) {
        serverService.delete(id);
    }

}
