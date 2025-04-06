package org.example.csiapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkInfo {
    private long bytesReceived;
    private long bytesSent;
    private long packetsReceived;
    private long packetsSent;
}