package com.mehdi.Laboratory.bdd.steps;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehdi.Laboratory.LaboratoryApplication;
import com.fasterxml.jackson.core.type.TypeReference;

import com.mehdi.Laboratory.bdd.enums.ParamType;
import com.mehdi.Laboratory.config.TestContext;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = LaboratoryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
@ActiveProfiles("test")
public class AbstractSteps {

    private final TestContext CONTEXT = TestContext.CONTEXT;

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port;
    };

    protected TestContext testContext() {
        return CONTEXT;
    }

    protected void executeGet(String servicePath, Map<String, String> pathParams, Map<String, String> queryParams) {
        CONTEXT.removeOldRequest();
        final RequestSpecification request = CONTEXT.getRequest();
        final String url = baseUrl() + servicePath;

        setQueryParams(queryParams, request);
        setPathParams(pathParams, request);

        Response response = request.accept(ContentType.JSON).get(url);
        logResponse(response);
        CONTEXT.setResponse(response);

    }

    protected void executePost(String servicePath, Map<String, String> pathParams, Map<String, String> queryParams, ParamType paramType) {
        CONTEXT.removeOldRequest();
        final RequestSpecification request = CONTEXT.getRequest();
        final Object payload = CONTEXT.getPayload();
        final String url = baseUrl() + servicePath;

        setQueryParams(queryParams, request);
        setPathParams(pathParams, request);

        if (paramType.isAsBody()) {
            setPayload(request, payload, paramType);
        } else {
            setParam(request, payload);
        }

        Response response = request.accept(ContentType.JSON).post(url);

        CONTEXT.setResponse(response);
    }

    private void setParam(RequestSpecification request, Object payload) {
        if (payload instanceof Map) {
            request.params((Map) payload);
        } else {
            Map<String, Object> map = new HashMap<>();
            new BeanMap(payload).forEach((key, value) -> map.put(key.toString(), value));
            request.params(map);
        }
    }

    private void setPayload(RequestSpecification request, Object payload, ParamType paramType) {
        if (null != payload) {
            request.contentType(paramType.getContentType().withCharset("UTF-8"));
            if (paramType == ParamType.JSON) {
                request.body(payload);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> params = objectMapper.convertValue(payload, new TypeReference<>() {});
                request.formParams(params);
            }
        }
    }

    private void setQueryParams(Map<String, String> queryParams, RequestSpecification request) {
        if (null != queryParams) {
            request.queryParams(queryParams);
        }
    }

    private void setPathParams(Map<String, String> pathParams, RequestSpecification request) {
        if (null != pathParams) {
            request.pathParams(pathParams);
        }
    }

    private void logResponse(Response response) {
        response
                .then()
                .log()
                .all();
    }

}
