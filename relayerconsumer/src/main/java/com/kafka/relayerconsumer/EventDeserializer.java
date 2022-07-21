package com.kafka.relayerconsumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class EventDeserializer implements Deserializer<RelayerInfo> {

    @Override
    public RelayerInfo deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RelayerInfo event = null;
        try {
            event = mapper.readValue(bytes, RelayerInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}
