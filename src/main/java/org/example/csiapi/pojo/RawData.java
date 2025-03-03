package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawData {
    private Long id;
    private String deviceId;
    private Long timestamp;
    private String csiData;
}
