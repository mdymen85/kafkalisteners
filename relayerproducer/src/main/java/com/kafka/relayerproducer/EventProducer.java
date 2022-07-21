package com.kafka.relayerproducer;

import lombok.Builder;
import lombok.Data;

@Data
public class EventProducer {

    private String uuid;
    private String name;

    public EventProducer() {

    }

    @Builder
    public EventProducer(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}
