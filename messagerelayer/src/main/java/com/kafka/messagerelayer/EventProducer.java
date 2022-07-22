package com.kafka.messagerelayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
