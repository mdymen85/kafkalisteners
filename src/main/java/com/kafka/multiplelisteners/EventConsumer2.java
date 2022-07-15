package com.kafka.multiplelisteners;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "camel_table2")
public class EventConsumer2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String number;

    public EventConsumer2() {

    }

    @Builder
    public EventConsumer2(Long id, String uuid, String number) {
        this.id = id;
        this.uuid = uuid;
        this.number = number;
    }
}