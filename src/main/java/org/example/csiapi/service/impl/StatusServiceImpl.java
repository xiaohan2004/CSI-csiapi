package org.example.csiapi.service.impl;

import org.example.csiapi.mapper.StatusMapper;
import org.example.csiapi.pojo.Status;
import org.example.csiapi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
