package com.kafka.messagerelayer;

import lombok.Builder;
import lombok.Data;

@Data
public class RelayerOutbox {

    private Long id;
    private String headers;
    private String payload;

    public RelayerOutbox() {}

    @Builder
    public RelayerOutbox(String headers, String payload) {
        this.headers = headers;
        this.payload = payload;
    }
}
