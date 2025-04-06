package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInfo {
    private long pid;
    private String name;
    private double cpuUsage;
    private long memoryUsed;
    private String user;
} 