package com.kafka.multiplelisteners;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class EventDeserializer implements Deserializer<EventConsumer> {

    @Override
    public EventConsumer deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventConsumer event = null;
        try {
            event = mapper.readValue(bytes, EventConsumer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}
