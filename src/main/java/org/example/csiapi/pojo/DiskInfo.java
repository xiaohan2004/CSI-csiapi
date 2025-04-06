package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiskInfo {
    private String name;
    private long totalSpace;
    private long freeSpace;
    private long readBytes;
    private long writeBytes;
}