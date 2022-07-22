package com.kafka.relayerconsumer;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "relayer_outbox")
@Data
public class RelayerOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headers;
    private String payload;
    private String uuid;

    public RelayerOutbox() {}

    @Builder
    public RelayerOutbox(String headers, String payload, String uuid) {
        this.headers = headers;
        this.payload = payload;
        this.uuid = uuid;
    }
}
