package com.kafka.multiplelisteners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KafkaConsumer {

    private final EventRepository eventRepository;

    @KafkaListener(topics = "topic-camel-1", groupId = "test")
    public void consumer5(EventConsumer eventConsumer) {

        log.info("listener topic from camel, message {}", eventConsumer);

        eventRepository.camel_result_table1(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

}
