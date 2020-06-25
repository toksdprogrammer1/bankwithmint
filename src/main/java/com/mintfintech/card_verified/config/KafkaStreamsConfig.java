package com.mintfintech.card_verified.config;


import com.mintfintech.card_verified.stream.KafkaStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(KafkaStreams.class)
public class KafkaStreamsConfig {
}
