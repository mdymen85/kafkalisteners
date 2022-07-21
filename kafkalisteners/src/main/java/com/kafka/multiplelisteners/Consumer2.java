package com.kafka.multiplelisteners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
@ConditionalOnProperty(name = "application.consumer2.enabled", havingValue = "true", matchIfMissing = true)
public class Consumer2 {

    private final EventRepository eventRepository;

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g2")
    public void consumer2(EventConsumer eventConsumer) {

        log.info("listener 2, message {}", eventConsumer);

        eventRepository.insert2(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox2(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

}
