package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpuInfo {
    private double systemCpuLoad;
    private double processCpuLoad;
    private int coreCount;
    private String model;
}