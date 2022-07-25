package com.kafka.outboxconsumer;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "destiny_message")
public class DestinyMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;

    @CreationTimestamp
    private Instant created;

    public DestinyMessage() {

    }

    @Builder
    public DestinyMessage(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}
