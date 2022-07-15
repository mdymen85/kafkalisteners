package com.kafka.multiplelisteners.camel;

import com.kafka.multiplelisteners.EventConsumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ApacheProcessorCamel3 extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from camel_table3"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQueryCamel3())
                .log("log ${in.headers.uuid}")
                .to("sql:delete from camel_table3 where uuid = :#uuid")
                .to("kafka:topic-camel-1?brokers=localhost:9092&keySerializer=org.apache.kafka.common.serialization.StringSerializer&valueSerializer=com.kafka.multiplelisteners.EventSerializer")
                .end();

    }
}

class ProcessQueryCamel3 implements Processor {

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
