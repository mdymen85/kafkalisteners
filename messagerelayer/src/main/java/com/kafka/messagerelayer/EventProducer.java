package com.kafka.messagerelayer;

import lombok.Builder;
import lombok.Data;

@Data
public class EventProducer {

    private String header;
    private String payload;

    public EventProducer() {

    }

    @Builder
    public EventProducer(String header, String payload) {
        this.header = header;
        this.payload = payload;
    }

}
