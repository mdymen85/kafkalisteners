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
@ConditionalOnProperty(name = "application.consumer3.enabled", havingValue = "true", matchIfMissing = true)
public class Consumer3 {

    private final EventRepository eventRepository;

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g3")
    public void consumer3(EventConsumer eventConsumer) {

        log.info("listener 3, message {}", eventConsumer);

        eventRepository.insert3(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox3(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

}
