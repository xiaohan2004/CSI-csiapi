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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/status/latest")
    public Result getStatus() {
        return Result.success(StatusService.getNewStatus());
    }

    // 根据时间戳获取状态
    @GetMapping("/status/{timestamp}")
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
    @GetMapping("/status/latestById")
    public Result getLatestStatusById() {
        return Result.success(StatusService.getNewestStatusById());
    }

    // 获取最新状态（按开始时间）
    @GetMapping("/status/latestByStart")
    public Result getLatestStatusByStart() {
        return Result.success(StatusService.getNewestStatusByStart());
    }

    // 获取最新状态（按结束时间）
    @GetMapping("/status/latestByEnd")
    public Result getLatestStatusByEnd() {
        return Result.success(StatusService.getNewestStatusByEnd());
    }

    // 分页获取原始数据（降序）
    @GetMapping("/rawData/desc")
    public Result getRawDataDesc(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(rawDataService.getRawDataListDesc(page, pageSize));
    }

    // 分页获取原始数据（升序）
    @GetMapping("/rawData/asc")
    public Result getRawDataAsc(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(rawDataService.getRawDataListAsc(page, pageSize));
    }

    // 获取最新的原始数据
    @GetMapping("/rawData/latest")
    public Result getLatestRawData() {
        return Result.success(rawDataService.getNewestRawData());
    }

    // 根据时间戳获取原始数据
    @GetMapping("/rawData/{timestamp}")
    public Result getRawDataByTimestamp(@PathVariable Long timestamp) {
        RawData rawData = rawDataService.getRawDataByTimestamp(timestamp);
        if (rawData == null) {
            return Result.error("No data found for this timestamp");
        }
        return Result.success(rawData);
    }

    // 获取时间段内的原始数据
    @GetMapping("/rawData/between")
    public Result getRawDataBetween(
            @RequestParam Long startTime,
            @RequestParam Long endTime) {
        if (startTime >= endTime) {
            return Result.error("开始时间必须小于结束时间");
        }
        List<RawData> data = rawDataService.getRawDataBetween(startTime, endTime);
        if (data.isEmpty()) {
            return Result.error("该时间段内没有数据");
        }
        return Result.success(data);
    }

    // 获取时间段内的状态数据
    @GetMapping("/status/between")
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
}
