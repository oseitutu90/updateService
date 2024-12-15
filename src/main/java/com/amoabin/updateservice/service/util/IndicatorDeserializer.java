package com.amoabin.updateservice.service.util;

import com.google.gson.*;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;



@Configuration
public class IndicatorDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            return jsonArray.toString(); // Convert the array to a string
        } else if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            return jsonObject.toString(); // Convert the object to a string
        } else {
            return json.getAsString(); // Handle the case when indicator is already a string
        }
    }
}


