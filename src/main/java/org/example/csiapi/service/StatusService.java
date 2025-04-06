package org.example.csiapi.service;

import org.example.csiapi.pojo.Status;
import org.example.csiapi.pojo.StatusPage;

import java.util.List;

public interface StatusService {
    StatusPage getStatusListDesc(Integer page, Integer pageSize);
    StatusPage getStatusListAsc(Integer page, Integer pageSize);
    Status getNewStatus();
    Status getStatusByTimestamp(Long timestamp);
    Status getNewestStatusById();
    Status getNewestStatusByStart();
    Status getNewestStatusByEnd();
    List<Status> getStatusBetween(Long startTime, Long endTime);
    Long getCount();
}