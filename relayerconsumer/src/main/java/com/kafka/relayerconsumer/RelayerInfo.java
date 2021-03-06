package com.kafka.relayerconsumer;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "relayer_info")
public class RelayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;

    @CreationTimestamp
    private Instant created;

    public RelayerInfo() {

    }

    @Builder
    public RelayerInfo(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}
