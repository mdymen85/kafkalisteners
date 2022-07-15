package com.kafka.multiplelisteners;

import lombok.Builder;
import lombok.Data;
import org.springframework.kafka.annotation.EnableKafka;

import javax.persistence.*;

@Data
@Entity
@Table(name = "camel_table1")
public class EventConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String number;

    public EventConsumer() {

    }

    @Builder
    public EventConsumer(Long id, String uuid, String number) {
        this.id = id;
        this.uuid = uuid;
        this.number = number;
    }
}