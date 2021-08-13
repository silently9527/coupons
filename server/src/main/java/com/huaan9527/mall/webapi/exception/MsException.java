package com.huaan9527.mall.webapi.exception;

import lombok.Getter;

@Getter
public class MsException extends RuntimeException {

    private String module;

    public MsException(String module, String message) {
        super(message);
        this.module = module;
    }

    public MsException(String module, String message, Throwable cause) {
        super(message, cause);
        this.module = module;
    }

}
