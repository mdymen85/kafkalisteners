package com.kafka.multiplelisteners;

import lombok.Data;
import org.springframework.kafka.annotation.EnableKafka;

import javax.persistence.*;

@Data
@Entity
@Table(name = "camel_table")
public class EventConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String number;

}