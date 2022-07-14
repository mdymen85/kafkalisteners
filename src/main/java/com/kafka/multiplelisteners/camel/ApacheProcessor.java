package com.kafka.multiplelisteners.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class ApacheProcessor extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer:foo?period=1000")
                .setBody(constant("select * from camel_table"))
                .to("jdbc:datasource")
                .split(body()).streaming()
                .process(new ProcessQuery())
                .log("log ${in.headers.uuid}")
                .to("sql:delete from sb_outbox where uuid = :#uuid")
                .marshal().json(JsonLibrary.Jackson)
                .to("spring-rabbitmq:amq.fanout")
                .end();

    }
}

class ProcessQuery implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
