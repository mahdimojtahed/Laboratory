package com.mehdi.Laboratory.dto;

import com.mehdi.Laboratory.shared.constants.ResponseCodePool;

import java.util.List;

public class BaseResponse {

    private String message;
    private Integer code;
    private List<AdditionalData> additionalData;

    public BaseResponse() {
    }

    public BaseResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public BaseResponse(String message, Integer code, List<AdditionalData> additionalData) {
        this.message = message;
        this.code = code;
        this.additionalData = additionalData;
    }

    public BaseResponse(ResponseCodePool responseCodePool) {
        this.message = responseCodePool.getMessage();
        this.code = responseCodePool.getCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setAdditionalData(List<AdditionalData> additionalData) {
        this.additionalData = additionalData;
    }

    public List<AdditionalData> getAdditionalData() {
        return additionalData;
    }
}
