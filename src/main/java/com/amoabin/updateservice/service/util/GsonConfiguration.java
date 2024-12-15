package com.amoabin.updateservice.service.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;

public class GsonConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(String.class, new IndicatorDeserializer())
                .create();
    }
}
