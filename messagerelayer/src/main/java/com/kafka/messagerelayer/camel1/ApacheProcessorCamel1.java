package com.kafka.messagerelayer.camel1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.messagerelayer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ConditionalOnProperty(name = "application.processor-camel.enabled", havingValue = "true", matchIfMissing = true)
@Component
@RequiredArgsConstructor
public class ApacheProcessorCamel1 extends RouteBuilder {

    private final ObjectMapper mapper;

    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from relayer_outbox limit 100"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQueryCamel1(mapper))
                .to("kafka:relayer-topic?brokers=localhost:9092")
                .log("log apache 1 ${in.headers.uuid}")
                .to("kafka:${spring.kafka.topic}?brokers=localhost:9092&keySerializer=org.apache.kafka.common.serialization.StringSerializer&valueSerializer=com.kafka.messagerelayer.EventSerializer")
                .to("sql:delete from relayer_outbox where uuid = :#uuid")
                .end();

    }
}

@RequiredArgsConstructor
class ProcessQueryCamel1 implements Processor {

    private final ObjectMapper mapper;

    @Override
    public void process(Exchange exchange) throws Exception {

        LinkedHashMap outbox = (LinkedHashMap) exchange.getIn().getBody();

        var headersString = outbox.get("headers").toString();
        var payload = outbox.get("payload").toString();

        var headers = List.of(headersString.split(","));
        List<Header> headersList = new ArrayList<Header>();
        headers.forEach(
                h -> {
                    var headerArray = h.split("=");
                    Header header = new RecordHeader(headerArray[0], headerArray[1].getBytes());
                    headersList.add(header);

                }
        );

        var eventProducer = mapper.readValue(payload, EventProducer.class);

        var record = new ProducerRecord<String, EventProducer>("relayer-topic", null, null, eventProducer, headersList);

        exchange.getIn().setBody(record);

        //var message = new Message
        exchange.getIn().setHeader("uuid", eventProducer.getUuid());
        exchange.getMessage().setBody(record);
    }
}
