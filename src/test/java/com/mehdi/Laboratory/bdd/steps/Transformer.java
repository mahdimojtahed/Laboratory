package com.mehdi.Laboratory.bdd.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DefaultDataTableEntryTransformer;

import java.lang.reflect.Type;
import java.util.Map;


public class Transformer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DefaultDataTableEntryTransformer
    public Object transform(Map<String, String> entry, Type type) {
        return objectMapper.convertValue(entry, objectMapper.constructType(type));
    }

}