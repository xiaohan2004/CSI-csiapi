package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    private Long id;
    private String deviceId;
    private Long startTimestamp;
    private Long endTimestamp;
    private Integer status;
    private Float confidence;
}
