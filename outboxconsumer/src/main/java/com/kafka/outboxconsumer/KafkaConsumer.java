package com.kafka.outboxconsumer;

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

    private final DestinyMessageRepository destinyMessageRepository;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0))
    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "g1")
    public void consumer1(ConsumerRecord<String, DestinyMessage> record) throws JsonProcessingException {

        try {

            var destinyMessage = record.value();

            log.info("Consumer is reading message {} from topic {}", destinyMessage, record.topic());

            destinyMessageRepository.save(destinyMessage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
