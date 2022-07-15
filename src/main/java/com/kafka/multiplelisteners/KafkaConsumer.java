package com.kafka.multiplelisteners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final EventRepository eventRepository;

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g1")
    public void consumer1(EventConsumer eventConsumer) {

        log.info("listener 1, message {}", eventConsumer);

        eventRepository.save(eventConsumer);

    }

    @KafkaListener(topics = "${application.topic.listener2}", groupId = "g1")
    public void consumer2(EventConsumer eventConsumer) {

        log.info("listener 2, message {}", eventConsumer);

        eventRepository.save(eventConsumer);
    }

    @KafkaListener(topics = "${application.topic.listener3}", groupId = "g1")
    public void consumer3(EventConsumer eventConsumer) {

        log.info("listener 3, message {}", eventConsumer);

        eventRepository.save(eventConsumer);
    }

    @KafkaListener(topics = "${application.topic.listener4}", groupId = "g1")
    public void consumer4(EventConsumer eventConsumer) {

        log.info("listener 4, message {}", eventConsumer);

        eventRepository.save(eventConsumer);
    }

    @KafkaListener(topics = "topic-camel-1", groupId = "g1")
    public void consumer5(EventConsumer eventConsumer) {

        log.info("listener 4, message {}", eventConsumer);

   //     eventRepository.save(eventConsumer);
    }

}
