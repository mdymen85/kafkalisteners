package com.kafka.multiplelisteners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final KafkaTemplate<String, EventConsumer> kafkaTemplate;
    private final KafkaTemplate<String, EventConsumer2> kafkaTemplate2;

    @RequestMapping(path = "/v1/process", method = RequestMethod.POST)
    public void process(@RequestBody EventConsumer eventConsumer) {

        int random = new Random().nextInt(1,4);

        var topic = "topic-listener" + random;

        log.info("sending message {} to topic {}", eventConsumer, topic);

        kafkaTemplate.send(topic, eventConsumer);

    }

    @RequestMapping(path = "/v1/process2", method = RequestMethod.POST)
    public void process2(@RequestBody EventConsumer2 eventConsumer2) {

        int random = new Random().nextInt(1,4);

        var topic = "topic-listener" + random;

        log.info("sending message {} to topic {}", eventConsumer2, topic);

        kafkaTemplate2.send(topic, eventConsumer2);

    }

}
