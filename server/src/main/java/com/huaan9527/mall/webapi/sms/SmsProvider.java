package com.huaan9527.mall.webapi.sms;

public interface SmsProvider {

    SendSmsResponse sendSms(SendSmsRequest request);

}
