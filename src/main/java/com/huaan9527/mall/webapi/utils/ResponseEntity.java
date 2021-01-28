package com.huaan9527.mall.webapi.utils;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

/**
 * Created by silentwu on 2015/7/3.
 */
public class ResponseEntity extends HashMap<String, Object> {
    public static final String SUCCESSFUL_KEY = "success";
    public static final String RESPONSE_CODE_KEY = "responseCode";
    public static final String MESSAGE_KEY = "message";
    public static final String DEFAULT_DATA_KEY = "data";

    private static String dataKey = DEFAULT_DATA_KEY;

    public ResponseEntity() {
    }

    public ResponseEntity(String dataKey) {
        this.dataKey = dataKey;
    }

    public Object getData() {
        return this.get(this.dataKey);
    }

    @SuppressWarnings("unchecked")
    public <T extends ResponseEntity> T setData(Object data) {
        this.put(this.dataKey, data);
        return (T) this;
    }

    public static ResponseEntity success(String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, true);
        responseEntity.put(RESPONSE_CODE_KEY, HttpStatus.OK.value());
        responseEntity.put(MESSAGE_KEY, message);
        return responseEntity;
    }

    public static ResponseEntity success() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, true);
        responseEntity.put(RESPONSE_CODE_KEY, HttpStatus.OK.value());
        responseEntity.put(MESSAGE_KEY, "操作成功");
        return responseEntity;
    }

    public static ResponseEntity success(Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, true);
        responseEntity.put(RESPONSE_CODE_KEY, HttpStatus.OK.value());
        responseEntity.put(MESSAGE_KEY, "操作成功");
        responseEntity.put(dataKey, data);
        return responseEntity;
    }

    public static ResponseEntity success(String message, Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, true);
        responseEntity.put(RESPONSE_CODE_KEY, HttpStatus.OK.value());
        responseEntity.put(MESSAGE_KEY, message);
        responseEntity.put(dataKey, data);
        return responseEntity;
    }


    public static ResponseEntity success(int responseCode, String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, true);
        responseEntity.put(RESPONSE_CODE_KEY, responseCode);
        responseEntity.put(MESSAGE_KEY, message);
        return responseEntity;
    }

    public static ResponseEntity success(int responseCode, String message, Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, true);
        responseEntity.put(RESPONSE_CODE_KEY, responseCode);
        responseEntity.put(MESSAGE_KEY, message);
        responseEntity.put(dataKey, data);
        return responseEntity;
    }

    public static ResponseEntity failure(String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, false);
        responseEntity.put(RESPONSE_CODE_KEY, HttpStatus.SERVICE_UNAVAILABLE.value());
        responseEntity.put(MESSAGE_KEY, message);
        return responseEntity;
    }

    public static ResponseEntity failure(int responseCode, String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.put(SUCCESSFUL_KEY, false);
        responseEntity.put(RESPONSE_CODE_KEY, responseCode);
        responseEntity.put(MESSAGE_KEY, message);
        return responseEntity;
    }

    public static ResponseEntity failure(BindingResult result) {
        ResponseEntity responseEntity = new ResponseEntity();
        StringBuilder message = new StringBuilder();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            final String defaultMessage = fieldError.getDefaultMessage();
            message.append(defaultMessage).append(";");
        }
        if (message.length() > 0) {
            message.deleteCharAt(message.length() - 1);
        }
        responseEntity.put(SUCCESSFUL_KEY, false);
        responseEntity.put(RESPONSE_CODE_KEY, HttpStatus.SERVICE_UNAVAILABLE.value());
        responseEntity.put(MESSAGE_KEY, message);
        return responseEntity;
    }

}

