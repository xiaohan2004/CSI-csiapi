package org.example.csiapi.service.impl;

import org.example.csiapi.mapper.StatusMapper;
import org.example.csiapi.pojo.Status;
import org.example.csiapi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    private StatusMapper statusMapper;

    @Autowired
    public StatusServiceImpl(StatusMapper statusMapper) {
        this.statusMapper = statusMapper;
    }

    @Override
    public Status getNewStatus() {
        return statusMapper.getNewestStatusByEnd();
    }

    @Override
    public Status getStatusByTimestamp(Long timestamp) {
        return statusMapper.getStatusByTimestamp(timestamp);
    }

    @Override
    public Status getNewestStatusById() {
        return statusMapper.getNewestStatusById();
    }

    @Override
    public Status getNewestStatusByStart() {
        return statusMapper.getNewestStatusByStart();
    }

    @Override
    public Status getNewestStatusByEnd() {
        return statusMapper.getNewestStatusByEnd();
    }

    @Override
    public List<Status> getStatusBetween(Long startTime, Long endTime) {
        return statusMapper.getStatusBetween(startTime, endTime);
    }
}
