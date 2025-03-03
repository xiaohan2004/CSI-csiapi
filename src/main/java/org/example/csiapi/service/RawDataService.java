package org.example.csiapi.service;

import org.example.csiapi.pojo.RawData;

import java.util.List;

public interface RawDataService {
    RawData getNewestRawData();
    List<RawData> getRawDataListDesc(Integer page, Integer pageSize);
    List<RawData> getRawDataListAsc(Integer page, Integer pageSize);
    RawData getRawDataByTimestamp(Long timestamp);
}
