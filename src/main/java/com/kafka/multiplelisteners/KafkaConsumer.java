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

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g1")
    public void consumer1(EventConsumer eventConsumer) {

        log.info("listener 1, message {}", eventConsumer);

        eventRepository.insert1(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox1(eventConsumer.getUuid(), eventConsumer.getNumber());

    }

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g2")
    public void consumer2(EventConsumer eventConsumer) {

        log.info("listener 2, message {}", eventConsumer);

        eventRepository.insert2(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox2(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g3")
    public void consumer3(EventConsumer eventConsumer) {

        log.info("listener 3, message {}", eventConsumer);

        eventRepository.insert3(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox3(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

    @KafkaListener(topics = "${application.topic.listener1}", groupId = "g4")
    public void consumer4(EventConsumer eventConsumer) {

        log.info("listener 4, message {}", eventConsumer);

        eventRepository.insert4(eventConsumer.getUuid(), eventConsumer.getNumber());
        eventRepository.insert_outbox4(eventConsumer.getUuid(), eventConsumer.getNumber());
    }

    @KafkaListener(topics = "topic-camel-1", groupId = "g1")
    public void consumer5(EventConsumer eventConsumer) {

        log.info("listener 4, message {}", eventConsumer);

   //     eventRepository.save(eventConsumer);
    }

}
