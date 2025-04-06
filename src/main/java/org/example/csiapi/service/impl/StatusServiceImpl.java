package org.example.csiapi.service.impl;

import org.example.csiapi.mapper.StatusMapper;
import org.example.csiapi.pojo.Status;
import org.example.csiapi.pojo.StatusPage;
import org.example.csiapi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    private StatusMapper statusMapper;

    @Autowired
    public StatusServiceImpl(StatusMapper statusMapper) {
        this.statusMapper = statusMapper;
    }

    @Override
    @Transactional
    public StatusPage getStatusListDesc(Integer page, Integer pageSize) {
        Long total = statusMapper.getCount();
        List<Status> statusList = statusMapper.getStatusListDesc((page - 1) * pageSize, pageSize);
        return new StatusPage(total, statusList);
    }

    @Override
    @Transactional
    public StatusPage getStatusListAsc(Integer page, Integer pageSize) {
        Long total = statusMapper.getCount();
        List<Status> statusList = statusMapper.getStatusListAsc((page - 1) * pageSize, pageSize);
        return new StatusPage(total, statusList);
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

    @Override
    public Long getCount() {
        return statusMapper.getCount();
    }
}
