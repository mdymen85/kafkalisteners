package com.kafka.multiplelisteners.camel;

import com.kafka.multiplelisteners.EventConsumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@ConditionalOnProperty(name = "application.processor-camel4.enabled", havingValue = "true", matchIfMissing = true)
@Component
class ApacheProcessorCamel4 extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from outbox_camel_table4 limit 1000"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQueryCamel4())
                .log("log apache 4 ${in.headers.uuid}")
                .to("sql:delete from outbox_camel_table4 where uuid = :#uuid")
                .to("kafka:topic-camel-4?brokers=localhost:9092&keySerializer=org.apache.kafka.common.serialization.StringSerializer&valueSerializer=com.kafka.multiplelisteners.EventSerializer")
                .end();

    }
}

@ConditionalOnProperty(name = "application.processor-camel4.enabled", havingValue = "true", matchIfMissing = true)
class ProcessQueryCamel4 implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        LinkedHashMap outbox = (LinkedHashMap) exchange.getIn().getBody();

        var eventConsumer = EventConsumer.builder()
                .number(outbox.get("number").toString())
                .uuid(outbox.get("uuid").toString())
                .build();

        exchange.getIn().setBody(eventConsumer);

        //var message = new Message
        exchange.getIn().setHeader("uuid", outbox.get("uuid").toString());
        exchange.getMessage().setBody(eventConsumer);
    }
}
