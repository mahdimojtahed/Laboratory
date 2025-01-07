package com.mehdi.Laboratory.bdd.enums;

import io.restassured.http.ContentType;

public enum ParamType {

    QUERY_PARAM("query param", false, null),
    JSON("json body", true, ContentType.JSON),
    URL_ENCODED("url encoded body", true, ContentType.URLENC);

    private String description;
    private boolean asBody;
    private ContentType contentType;

    ParamType(String description, boolean asBody, ContentType contentType) {
        this.description = description;
        this.asBody = asBody;
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAsBody() {
        return asBody;
    }

    public void setAsBody(boolean asBody) {
        this.asBody = asBody;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
