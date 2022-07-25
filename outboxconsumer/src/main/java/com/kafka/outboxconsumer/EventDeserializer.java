package com.kafka.outboxconsumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class EventDeserializer implements Deserializer<DestinyMessage> {

    @Override
    public DestinyMessage deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DestinyMessage event = null;
        try {
            event = mapper.readValue(bytes, DestinyMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}
