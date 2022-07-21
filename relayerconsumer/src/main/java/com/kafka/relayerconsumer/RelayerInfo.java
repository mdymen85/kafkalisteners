package com.kafka.relayerconsumer;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "relayer_info")
public class RelayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;

    public RelayerInfo() {

    }

    @Builder
    public RelayerInfo(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}
