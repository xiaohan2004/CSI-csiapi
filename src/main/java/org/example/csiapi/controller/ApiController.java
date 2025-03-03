package org.example.csiapi.controller;

import org.example.csiapi.pojo.RawData;
import org.example.csiapi.pojo.Result;
import org.example.csiapi.pojo.Status;
import org.example.csiapi.service.RawDataService;
import org.example.csiapi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private StatusService StatusService;
    private RawDataService rawDataService;

    @Autowired
    public ApiController(StatusService StatusService, RawDataService rawDataService) {
        this.StatusService = StatusService;
        this.rawDataService = rawDataService;
    }

    // 获取最新状态
    @GetMapping("/getStatus")
    public Result getStatus() {
        return Result.success(StatusService.getNewStatus());
    }

    // 根据时间戳获取状态
    @GetMapping("/getStatus/{timestamp}")
    public Result getStatusByTimestamp(@PathVariable Long timestamp) {
        Status status = StatusService.getStatusByTimestamp(timestamp);
        if (status == null) {
            RawData rawData = rawDataService.getRawDataByTimestamp(timestamp);
            if (rawData == null) {
                // The device is offline during this period.
                return Result.error("OFFLINE");
            }
            // The data is being processed for this period.
            return Result.error("PROCESSING");
        }
        return Result.success(StatusService.getStatusByTimestamp(timestamp));
    }
}
