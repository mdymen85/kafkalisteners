package com.kafka.multiplelisteners.camel;

import com.kafka.multiplelisteners.EventConsumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
class ApacheProcessorCamel4 extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from camel_table4"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQueryCamel4())
                .log("log ${in.headers.uuid}")
                .to("sql:delete from camel_table4 where uuid = :#uuid")
                .to("kafka:topic-camel-1?brokers=localhost:9092&keySerializer=org.apache.kafka.common.serialization.StringSerializer&valueSerializer=com.kafka.multiplelisteners.EventSerializer")
                .end();

    }
}

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
