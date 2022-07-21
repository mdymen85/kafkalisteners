package com.kafka.multiplelisteners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class EventSerializer implements Serializer<EventConsumer> {

    @Override
    public byte[] serialize(String s, EventConsumer event) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(event).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

}