package com.kafka.messagerelayer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@ConditionalOnProperty(name = "application.processor-camel.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class ApacheProcessorCamel1 extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from relayer_outbox limit 100"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQueryCamel1())
                .to("kafka:relayer-topic?brokers=localhost:9092")
//                .log("log apache 1 ${in.headers.uuid}")
           //     .to("kafka:relayer-topic?brokers=localhost:9092&keySerializer=org.apache.kafka.common.serialization.StringSerializer&valueSerializer=com.kafka.multiplelisteners.EventSerializer")
                .to("sql:delete from relayer_outbox where uuid = :#uuid")
                .end();

    }
}

class ProcessQueryCamel1 implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        LinkedHashMap outbox = (LinkedHashMap) exchange.getIn().getBody();

        var eventConsumer = EventProducer.builder()
                .header(outbox.get("payload").toString())
                .payload(outbox.get("payload").toString())
                .build();

        var record = new ProducerRecord<String, EventProducer>("", eventConsumer.getPayload(), eventConsumer.getHeader());

        exchange.getIn().setBody(eventConsumer);

        //var message = new Message
        exchange.getIn().setHeader("uuid", outbox.get("uuid").toString());
        exchange.getMessage().setBody(eventConsumer);
    }
}
