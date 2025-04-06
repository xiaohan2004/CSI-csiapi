package org.example.csiapi.controller;

import org.example.csiapi.pojo.*;
import org.example.csiapi.service.ServerMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server")
public class ServerController {
    private final ServerMonitorService serverMonitorService;

    @Autowired
    public ServerController(ServerMonitorService serverMonitorService) {
        this.serverMonitorService = serverMonitorService;
    }

    @GetMapping("/info")
    public Result getServerInfo() {
        return Result.success(serverMonitorService.getServerInfo());
    }

    @GetMapping("/cpu")
    public Result getCpuInfo() {
        return Result.success(serverMonitorService.getCpuInfo());
    }

    @GetMapping("/memory")
    public Result getMemoryInfo() {
        return Result.success(serverMonitorService.getMemoryInfo());
    }

    @GetMapping("/disk")
    public Result getDiskInfo() {
        return Result.success(serverMonitorService.getDiskInfo());
    }

    @GetMapping("/network")
    public Result getNetworkInfo() {
        return Result.success(serverMonitorService.getNetworkInfo());
    }

    @GetMapping("/processes")
    public Result getTopProcesses(@RequestParam(defaultValue = "5") int limit) {
        return Result.success(serverMonitorService.getTopProcesses(limit));
    }
}
