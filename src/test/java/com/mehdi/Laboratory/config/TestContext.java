package com.mehdi.Laboratory.config;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public enum TestContext {

    CONTEXT;

    private static final String PAYLOAD = "PAYLOAD";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";
    private static final String TOKEN = "TOKEN";

    private final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>().withInitial(HashMap::new);

    public <T> T get(String key) {
        return (T) context.get().get(key);
    }

    public <T> T set(String key, T value) {
        context.get().put(key, value);
        return value;
    }

    public RequestSpecification getRequest() {
        if (null == get(REQUEST)) {
            set(REQUEST, given().log().all());
        }
        return get(REQUEST);
    }

    public void removeOldRequest() {
        if (null != get(REQUEST)) {
            context.get().remove(REQUEST);
        }
    }

    public Response getResponse() {
        return get(RESPONSE);
    }

    public void setResponse(Response response) {
        set(RESPONSE, response);
    }

    public Object getPayload() {
        return get(PAYLOAD);
    }

    public <T> T getPayload(Class<T> clazz) {
        return clazz.cast(getPayload());
    }

    public <T> void setPayload(T payload) {
        set(PAYLOAD, payload);
    }

    public void reset() {
        context.get().clear();
    }

}
