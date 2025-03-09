package org.example.csiapi.service.impl;

import org.example.csiapi.mapper.RawDataMapper;
import org.example.csiapi.pojo.RawData;
import org.example.csiapi.service.RawDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawDataServiceImpl implements RawDataService {
    private RawDataMapper rawDataMapper;

    @Autowired
    public RawDataServiceImpl(RawDataMapper rawDataMapper) {
        this.rawDataMapper = rawDataMapper;
    }

    @Override
    public RawData getNewestRawData() {
        return rawDataMapper.getNewestRawData();
    }

    @Override
    public List<RawData> getRawDataListDesc(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        return rawDataMapper.getRawDataListDesc(offset, pageSize);
    }

    @Override
    public List<RawData> getRawDataListAsc(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        return rawDataMapper.getRawDataListAsc(offset, pageSize);
    }

    @Override
    public RawData getRawDataByTimestamp(Long timestamp) {
        return rawDataMapper.getRawDataByTimestamp(timestamp);
    }

    @Override
    public List<RawData> getRawDataBetween(Long startTime, Long endTime) {
        return rawDataMapper.getRawDataBetween(startTime, endTime);
    }
}
