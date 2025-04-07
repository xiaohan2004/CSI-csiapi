package org.example.csiapi.controller;

import org.example.csiapi.pojo.Result;
import org.example.csiapi.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    @GetMapping
    public Result getAllOperateLogs() {
        return Result.success(operateLogService.getAllOperateLogs());
    }
}
