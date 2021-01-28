package com.huaan9527.mall.webapi.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsResponse {
    private int code;
    private String message;

    public SendSmsResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
