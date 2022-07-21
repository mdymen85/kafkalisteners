package com.kafka.multiplelisteners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
@ConditionalOnProperty(name = "application.consumer1.enabled", havingValue = "true", matchIfMissing = true)
public class Consumer1 {

    private final EventRepository eventRepository;

    @RetryableTopic
    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g1")
    public void consumer1(EventConsumer eventConsumer) {

        log.info("listener 1, message {}", eventConsumer);

        eventRepository.insert1(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox1(eventConsumer.getUuid(), eventConsumer.getNumber());

    }

}
