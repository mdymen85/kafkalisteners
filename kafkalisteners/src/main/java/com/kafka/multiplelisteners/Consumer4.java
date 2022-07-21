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
@ConditionalOnProperty(name = "application.consumer4.enabled", havingValue = "true", matchIfMissing = true)
public class Consumer4 {

    private final EventRepository eventRepository;

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g4")
    public void consumer4(EventConsumer eventConsumer) {

        log.info("listener 4, message {}", eventConsumer);

        eventRepository.insert4(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox4(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

}
