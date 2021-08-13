package com.huaan9527.mall.webapi.sms;

import com.huaan9527.mall.webapi.exception.MsException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractSmsProvider implements SmsProvider {

    @Override
    public SendSmsResponse sendSms(SendSmsRequest request) {
        validateRequest(request);
        return executeSendSms(request);
    }

    protected abstract SendSmsResponse executeSendSms(SendSmsRequest request);

    private void validateRequest(SendSmsRequest request) {
        if (CollectionUtils.isEmpty(request.getPhones())) {
            throw new MsException("sms", "手机号不能为空");
        }
        if (StringUtils.isEmpty(request.getTemplateId())) {
            throw new MsException("sms", "模板id不能为空");
        }
    }


}
