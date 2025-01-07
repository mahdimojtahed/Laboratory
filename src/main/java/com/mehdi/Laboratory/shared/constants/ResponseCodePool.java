package com.mehdi.Laboratory.shared.constants;

public enum ResponseCodePool {

    SUCCESS(0, "REQUEST_PROCESSED_SUCCESSFULLY"),
    REQUIRED_PARAMS_NOT_PROVIDED(1, "REQUIRED_PARAMS_NOT_PROVIDED");

    private final Integer code;
    private final String message;

    ResponseCodePool(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
