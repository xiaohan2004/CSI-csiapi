package org.example.csiapi.service;

import org.example.csiapi.pojo.Status;

import java.util.List;

public interface StatusService {
    Status getNewStatus();
    Status getStatusByTimestamp(Long timestamp);
    Status getNewestStatusById();
    Status getNewestStatusByStart();
    Status getNewestStatusByEnd();
    List<Status> getStatusBetween(Long startTime, Long endTime);
}