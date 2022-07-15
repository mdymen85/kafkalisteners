package com.kafka.multiplelisteners;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "camel_table3")
public class EventConsumer3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String number;

    public EventConsumer3() {

    }

    @Builder
    public EventConsumer3(Long id, String uuid, String number) {
        this.id = id;
        this.uuid = uuid;
        this.number = number;
    }
}