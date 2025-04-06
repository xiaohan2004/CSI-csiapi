package org.example.csiapi.service;

import org.example.csiapi.pojo.*;
import java.util.List;

public interface ServerMonitorService {
    CpuInfo getCpuInfo();
    MemoryInfo getMemoryInfo();
    List<DiskInfo> getDiskInfo();
    NetworkInfo getNetworkInfo();
    List<ProcessInfo> getTopProcesses(int limit);
    ServerInfo getServerInfo();
} 