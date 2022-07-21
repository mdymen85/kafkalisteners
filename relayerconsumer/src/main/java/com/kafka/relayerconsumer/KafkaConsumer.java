package com.kafka.relayerconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KafkaConsumer {

    private final RelayerOutboxRepository relayerOutboxRepository;
    private final RelayerInfoRepository relayerInfoRepository;
    private final ObjectMapper mapper;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0))
    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "g1")
    public void consumer1(ConsumerRecord<String, RelayerInfo> record) throws JsonProcessingException {

        try {

            var relayerInfo = record.value();
            var headers = record.headers().toString();

            log.info("consumer is reading message {} from topic {}", relayerInfo, record.topic());


            var relayerOutbox = RelayerOutbox
                    .builder()
                    .headers(headers)
                    .payload(mapper.writeValueAsString(relayerInfo))
                    .build();

            relayerInfoRepository.save(relayerInfo);
            relayerOutboxRepository.save(relayerOutbox);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
