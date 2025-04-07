package org.example.csiapi.service.impl;

import org.example.csiapi.mapper.OperateLogMapper;
import org.example.csiapi.pojo.OperateLog;
import org.example.csiapi.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public List<OperateLog> getAllOperateLogs() {
        return operateLogMapper.getAllOperateLogs();
    }

}
