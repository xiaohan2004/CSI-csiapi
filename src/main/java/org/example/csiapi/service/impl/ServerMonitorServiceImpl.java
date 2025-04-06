package org.example.csiapi.service.impl;

import org.example.csiapi.pojo.*;
import org.example.csiapi.service.ServerMonitorService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerMonitorServiceImpl implements ServerMonitorService {
    private final SystemInfo systemInfo = new SystemInfo();
    private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private final OperatingSystem os = systemInfo.getOperatingSystem();

    @Override
    public CpuInfo getCpuInfo() {
        CentralProcessor processor = hardware.getProcessor();
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setSystemCpuLoad(processor.getSystemCpuLoad(1000));
        cpuInfo.setProcessCpuLoad(processor.getProcessorCpuLoad(1000)[0]);
        cpuInfo.setCoreCount(processor.getLogicalProcessorCount());
        cpuInfo.setModel(processor.getProcessorIdentifier().getName());
        return cpuInfo;
    }

    @Override
    public MemoryInfo getMemoryInfo() {
        GlobalMemory memory = hardware.getMemory();
        MemoryInfo memoryInfo = new MemoryInfo();
        memoryInfo.setTotal(memory.getTotal());
        memoryInfo.setFree(memory.getAvailable());
        memoryInfo.setUsed(memory.getTotal() - memory.getAvailable());
        memoryInfo.setUsageRate((double) memoryInfo.getUsed() / memoryInfo.getTotal() * 100);
        return memoryInfo;
    }

    @Override
    public List<DiskInfo> getDiskInfo() {
        List<DiskInfo> diskInfos = new ArrayList<>();
        for (HWDiskStore disk : hardware.getDiskStores()) {
            DiskInfo diskInfo = new DiskInfo();
            diskInfo.setName(disk.getName());
            diskInfo.setTotalSpace(disk.getSize());
            diskInfo.setReadBytes(disk.getReadBytes());
            diskInfo.setWriteBytes(disk.getWriteBytes());
            diskInfos.add(diskInfo);
        }
        return diskInfos;
    }

    @Override
    public NetworkInfo getNetworkInfo() {
        NetworkInfo networkInfo = new NetworkInfo();
        for (NetworkIF network : hardware.getNetworkIFs()) {
            networkInfo.setBytesReceived(networkInfo.getBytesReceived() + network.getBytesRecv());
            networkInfo.setBytesSent(networkInfo.getBytesSent() + network.getBytesSent());
            networkInfo.setPacketsReceived(networkInfo.getPacketsReceived() + network.getPacketsRecv());
            networkInfo.setPacketsSent(networkInfo.getPacketsSent() + network.getPacketsSent());
        }
        return networkInfo;
    }

    @Override
    public List<ProcessInfo> getTopProcesses(int limit) {
        return os.getProcesses().stream()
                .sorted((p1, p2) -> Double.compare(p2.getProcessCpuLoadBetweenTicks(null), 
                        p1.getProcessCpuLoadBetweenTicks(null)))
                .limit(limit)
                .map(this::convertToProcessInfo)
                .collect(Collectors.toList());
    }

    private ProcessInfo convertToProcessInfo(OSProcess process) {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setPid(process.getProcessID());
        processInfo.setName(process.getName());
        processInfo.setCpuUsage(process.getProcessCpuLoadBetweenTicks(null) * 100);
        processInfo.setMemoryUsed(process.getResidentSetSize());
        processInfo.setUser(process.getUser());
        return processInfo;
    }

    @Override
    public ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setCpu(getCpuInfo());
        serverInfo.setMemory(getMemoryInfo());
        serverInfo.setDisks(getDiskInfo());
        serverInfo.setNetwork(getNetworkInfo());
        serverInfo.setTopProcesses(getTopProcesses(5));
        return serverInfo;
    }
} 