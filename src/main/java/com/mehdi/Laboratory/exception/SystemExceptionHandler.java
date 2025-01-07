package com.mehdi.Laboratory.exception;


import com.mehdi.Laboratory.dto.BaseResponse;
import com.mehdi.Laboratory.shared.constants.ResponseCodePool;
import com.mehdi.Laboratory.shared.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class SystemExceptionHandler {

    @ExceptionHandler(BindException.class)
    private ResponseEntity<BaseResponse> handleBindException(BindException e) {
        List<String> errorMessages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> StringUtils.defaultIfEmpty(fieldError.getDefaultMessage(), "INVALID_PARAM"))
                .toList();

        BaseResponse response = new BaseResponse(
                String.join(", ", errorMessages),
                ResponseCodePool.REQUIRED_PARAMS_NOT_PROVIDED.getCode()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException e) {
        BaseResponse response = new BaseResponse(e.getMessage(), ResponseCodePool.REQUIRED_PARAMS_NOT_PROVIDED.getCode());
        return ResponseEntity.badRequest().body(response);
    }
}
