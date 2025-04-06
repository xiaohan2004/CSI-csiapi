package org.example.csiapi.controller;

import org.example.csiapi.pojo.RawData;
import org.example.csiapi.pojo.Result;
import org.example.csiapi.service.RawDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rawData")
public class RawDataController {
    private RawDataService rawDataService;

    @Autowired
    public RawDataController(RawDataService rawDataService) {
        this.rawDataService = rawDataService;
    }

    // 分页获取原始数据（降序）
    @GetMapping("/desc")
    public Result getRawDataDesc(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(rawDataService.getRawDataListDesc(page, pageSize));
    }

    // 分页获取原始数据（升序）
    @GetMapping("/asc")
    public Result getRawDataAsc(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(rawDataService.getRawDataListAsc(page, pageSize));
    }

    // 获取最新的原始数据
    @GetMapping("/latest")
    public Result getLatestRawData() {
        return Result.success(rawDataService.getNewestRawData());
    }

    // 根据时间戳获取原始数据
    @GetMapping("/{timestamp}")
    public Result getRawDataByTimestamp(@PathVariable Long timestamp) {
        RawData rawData = rawDataService.getRawDataByTimestamp(timestamp);
        if (rawData == null) {
            return Result.error("No data found for this timestamp");
        }
        return Result.success(rawData);
    }

    // 获取时间段内的原始数据
    @GetMapping("/between")
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

    // 获取原始数据总数
    @GetMapping("/count")
    public Result getRawDataCount() {
        return Result.success(rawDataService.getCount());
    }
} 