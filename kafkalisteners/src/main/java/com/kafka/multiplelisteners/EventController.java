package com.kafka.multiplelisteners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    @Value("${application.topic.listener1}")
    private String topic;

    private final KafkaTemplate<String, EventConsumer> kafkaTemplate;

    @RequestMapping(path = "/v1/process", method = RequestMethod.POST)
    public void process(@RequestBody EventConsumer eventConsumer) {

        log.info("sending message {} to topic {}", eventConsumer, topic);

        kafkaTemplate.send(topic, eventConsumer);

    }

}
