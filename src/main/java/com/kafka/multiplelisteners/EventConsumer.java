package com.kafka.multiplelisteners;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EventConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String number;

    public EventConsumer() {

    }

    @Builder
    public EventConsumer(String uuid, String number) {
        this.uuid = uuid;
        this.number = number;
    }

}
