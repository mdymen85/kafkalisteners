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

    public RelayerOutbox() {}

    @Builder
    public RelayerOutbox(String headers, String payload) {
        this.headers = headers;
        this.payload = payload;
    }
}
