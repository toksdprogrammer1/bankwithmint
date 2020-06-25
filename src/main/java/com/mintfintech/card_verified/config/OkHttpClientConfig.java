package com.mintfintech.card_verified.config;

import okhttp3.OkHttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OkHttpClientConfig {

    @Bean
    public OkHttpClient createClient() {

        return new OkHttpClient().newBuilder().build();

    }
}
