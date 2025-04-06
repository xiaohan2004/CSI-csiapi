package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoryInfo {
    private long total;
    private long used;
    private long free;
    private double usageRate;
}