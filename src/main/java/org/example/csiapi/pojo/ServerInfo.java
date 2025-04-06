package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    private CpuInfo cpu;
    private MemoryInfo memory;
    private List<DiskInfo> disks;
    private NetworkInfo network;
    private List<ProcessInfo> topProcesses;
}