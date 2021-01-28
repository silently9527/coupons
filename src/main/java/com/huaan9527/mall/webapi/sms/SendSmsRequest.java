package com.huaan9527.mall.webapi.sms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SendSmsRequest {
    private String templateId;
    private List<String> phones;
    private Map<String, String> params;
}
