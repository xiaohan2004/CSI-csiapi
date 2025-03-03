package org.example.csiapi.service;

import org.example.csiapi.pojo.Status;

public interface StatusService {
    Status getNewStatus();
    Status getStatusByTimestamp(Long timestamp);
}