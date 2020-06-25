package com.mintfintech.card_verified.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import static org.springframework.cloud.stream.messaging.Sink.INPUT;

public interface KafkaStreams {

    String INPUT = "consume_topic";
    String OUTPUT = "publish_topic";


    @Input(INPUT)
    SubscribableChannel consume();

    @Output(OUTPUT)
    MessageChannel initiate();

}
