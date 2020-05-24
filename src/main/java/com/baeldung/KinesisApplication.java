package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding(Processor.class)
public class KinesisApplication {

    public static void main(String[] args) {
        System.setProperty("AWS_CBOR_DISABLE", "1");
        System.setProperty("com.amazonaws.sdk.disableCbor", "1");
        SpringApplication.run(KinesisApplication.class, args);
    }

    @Autowired
    private Processor processor;

    @StreamListener(Processor.INPUT)
    public void consume(String val) {
        System.out.println("\n \n received event data : "+val);
    }

//    public void produce(String val) {
//        processor.output().send(MessageBuilder.withPayload(val).build());
//    }
}
