package com.kafka.relayerconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KafkaConsumer {

    private final RelayerOutboxRepository relayerOutboxRepository;
    private final RelayerInfoRepository relayerInfoRepository;
    private final ObjectMapper mapper;
    private final Tracer tracer;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0))
    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "g1")
    public void consumer1(ConsumerRecord<String, RelayerInfo> record) throws JsonProcessingException {

        try {

            var relayerInfo = record.value();
            var headers = this.constructHeaders(record.headers());

            log.info("Headers {}", headers);
            log.info("Consumer is reading message {} from topic {}", relayerInfo, record.topic());


            var relayerOutbox = RelayerOutbox
                    .builder()
                    .headers(headers)
                    .payload(mapper.writeValueAsString(relayerInfo))
                    .uuid(tracer.currentSpan().context().traceId())
                    .build();

            relayerInfoRepository.save(relayerInfo);
            relayerOutboxRepository.save(relayerOutbox);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String constructHeaders(Headers headers) {
        final Map<String, String> attributes = new HashMap<>();
        StringBuilder strHeaders = new StringBuilder();
        int i = 0;
        for (final Header header : headers) {
            if (i != 0) {
                strHeaders.append(",");
            }
            strHeaders.append(header.key())
                    .append("=")
                    .append(header.value());
            i++;
        }
        return strHeaders.toString();
    }

}
