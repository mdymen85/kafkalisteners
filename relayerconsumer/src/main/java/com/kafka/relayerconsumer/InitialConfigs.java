package com.kafka.relayerconsumer;

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

    @Value("${spring.kafka.consumer-group:group1}")
    private String consumerGroup;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @PostConstruct
    public void initialEnvironmentVariables() {
        log.info("spring.kafka.bootstrap-servers: {}", bootstrap);
        log.info("spring.kafka.topic: {}", topic);
        log.info("spring.kafka.consumer-group: {}", consumerGroup);
        log.info("spring.datasource.url: {}", datasourceUrl);
        log.info("spring.datasource.username: {}", datasourceUsername);
        log.info("spring.datasource.password: {}", datasourcePassword);

    }

}
