package com.kafka.multiplelisteners.camel;

import com.kafka.multiplelisteners.EventConsumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@ConditionalOnProperty(name = "application.processor-camel2.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class ApacheProcessorCamel2 extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from outbox_camel_table2 limit 1000"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQueryCamel2())
                .log("log apache 2 ${in.headers.uuid}")
                .to("sql:delete from outbox_camel_table2 where uuid = :#uuid")
                .to("kafka:topic-camel-2?brokers=localhost:9092&keySerializer=org.apache.kafka.common.serialization.StringSerializer&valueSerializer=com.kafka.multiplelisteners.EventSerializer")
                .end();

    }
}

@ConditionalOnProperty(name = "application.processor-camel2.enabled", havingValue = "true", matchIfMissing = true)
class ProcessQueryCamel2 implements Processor {

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
