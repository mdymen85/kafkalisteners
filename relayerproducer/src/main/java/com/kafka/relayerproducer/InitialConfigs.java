package com.kafka.relayerproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class InitialConfigs {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrap;

    @Value("${spring.kafka.topic:relayer-topic}")
    private String topic;

    @PostConstruct
    public void initialEnvironmentVariables() {
        log.info("spring.kafka.bootstrap-servers: {}", bootstrap);
        log.info("spring.kafka.topic: {}", topic);
    }

}
