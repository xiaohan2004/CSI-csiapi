package org.example.csiapi.controller;

import org.example.csiapi.pojo.RawData;
import org.example.csiapi.pojo.Result;
import org.example.csiapi.pojo.Status;
import org.example.csiapi.service.RawDataService;
import org.example.csiapi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private StatusService StatusService;
    private RawDataService rawDataService;

    @Autowired
    public StatusController(StatusService StatusService, RawDataService rawDataService) {
        this.StatusService = StatusService;
        this.rawDataService = rawDataService;
    }

    @GetMapping("/light")
    public int getLightStatus() {
        Status status = StatusService.getNewStatus();
        return status.getStatus();
    }

    // 分页获取状态（降序）
    @GetMapping("/desc")
    public Result getStatusDesc(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(StatusService.getStatusListDesc(page, pageSize));
    }

    // 分页获取状态（升序）
    @GetMapping("/asc")
    public Result getStatusAsc(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(StatusService.getStatusListAsc(page, pageSize));
    }

    // 获取最新状态
    @GetMapping("/latest")
    public Result getStatus() {
        return Result.success(StatusService.getNewStatus());
    }

    // 根据时间戳获取状态
    @GetMapping("/{timestamp}")
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

    // 获取最新状态（按ID）
    @GetMapping("/latestById")
    public Result getLatestStatusById() {
        return Result.success(StatusService.getNewestStatusById());
    }

    // 获取最新状态（按开始时间）
    @GetMapping("/latestByStart")
    public Result getLatestStatusByStart() {
        return Result.success(StatusService.getNewestStatusByStart());
    }

    // 获取最新状态（按结束时间）
    @GetMapping("/latestByEnd")
    public Result getLatestStatusByEnd() {
        return Result.success(StatusService.getNewestStatusByEnd());
    }

    // 获取时间段内的状态数据
    @GetMapping("/between")
    public Result getStatusBetween(
            @RequestParam Long startTime,
            @RequestParam Long endTime) {
        if (startTime >= endTime) {
            return Result.error("开始时间必须小于结束时间");
        }
        List<Status> data = StatusService.getStatusBetween(startTime, endTime);
        if (data.isEmpty()) {
            return Result.error("该时间段内没有数据");
        }
        return Result.success(data);
    }

    // 获取状态数据总数
    @GetMapping("/count") 
    public Result getStatusCount() {
        return Result.success(StatusService.getCount());
    }

    @PostMapping
    public Result createStatus(@RequestBody Status status) {
        Status createdStatus = StatusService.createStatus(status);
        return Result.success(createdStatus);
    }

    @PutMapping("/{id}")
    public Result updateStatus(@PathVariable Long id, @RequestBody Status status) {
        Status updatedStatus = StatusService.updateStatus(id, status);
        if (updatedStatus == null) {
            return Result.error("状态数据不存在");
        }
        return Result.success(updatedStatus);
    }

    @DeleteMapping("/{id}")
    public Result deleteStatus(@PathVariable Long id) {
        if (StatusService.deleteStatus(id)) {
            return Result.success(null);
        }
        return Result.error("状态数据不存在");
    }
} 