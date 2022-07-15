package com.kafka.multiplelisteners;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "camel_table4")
public class EventConsumer4 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String number;

    public EventConsumer4() {

    }

    @Builder
    public EventConsumer4(Long id, String uuid, String number) {
        this.id = id;
        this.uuid = uuid;
        this.number = number;
    }
}