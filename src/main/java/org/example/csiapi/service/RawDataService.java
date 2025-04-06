package org.example.csiapi.service;

import org.example.csiapi.pojo.RawData;
import org.example.csiapi.pojo.RawDataPage;

import java.util.List;

public interface RawDataService {
    RawData getNewestRawData();
    RawDataPage getRawDataListDesc(Integer page, Integer pageSize);
    RawDataPage getRawDataListAsc(Integer page, Integer pageSize);
    RawData getRawDataByTimestamp(Long timestamp);
    List<RawData> getRawDataBetween(Long startTime, Long endTime);
    Long getCount();
    RawData createRawData(RawData rawData);
    RawData updateRawData(Long id, RawData rawData);
    boolean deleteRawData(Long id);
    RawData getRawDataById(Long id);
}
