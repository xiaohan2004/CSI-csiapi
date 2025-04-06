package org.example.csiapi.service.impl;

import org.example.csiapi.mapper.RawDataMapper;
import org.example.csiapi.pojo.RawData;
import org.example.csiapi.pojo.RawDataPage;
import org.example.csiapi.service.RawDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public RawDataPage getRawDataListDesc(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        List<RawData> rawData = rawDataMapper.getRawDataListDesc(offset, pageSize);
        Long total = rawDataMapper.getCount();
        RawDataPage rawDataPage = new RawDataPage(total, rawData);
        return rawDataPage;
    }

    @Override
    @Transactional
    public RawDataPage getRawDataListAsc(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        List<RawData> rawData = rawDataMapper.getRawDataListAsc(offset, pageSize);
        Long total = rawDataMapper.getCount();
        RawDataPage rawDataPage = new RawDataPage(total, rawData);
        return rawDataPage;
    }

    @Override
    public RawData getRawDataByTimestamp(Long timestamp) {
        return rawDataMapper.getRawDataByTimestamp(timestamp);
    }

    @Override
    public List<RawData> getRawDataBetween(Long startTime, Long endTime) {
        return rawDataMapper.getRawDataBetween(startTime, endTime);
    }

    @Override
    public Long getCount() {
        return rawDataMapper.getCount();
    }

    @Override
    @Transactional
    public RawData createRawData(RawData rawData) {
        rawDataMapper.insert(rawData);
        return rawData;
    }

    @Override
    @Transactional
    public RawData updateRawData(Long id, RawData rawData) {
        RawData existingRawData = rawDataMapper.findById(id);
        if (existingRawData == null) {
            return null;
        }
        rawData.setId(id);
        rawDataMapper.update(rawData);
        return rawData;
    }

    @Override
    @Transactional
    public boolean deleteRawData(Long id) {
        return rawDataMapper.deleteById(id) > 0;
    }

    @Override
    public RawData getRawDataById(Long id) {
        return rawDataMapper.findById(id);
    }
}
