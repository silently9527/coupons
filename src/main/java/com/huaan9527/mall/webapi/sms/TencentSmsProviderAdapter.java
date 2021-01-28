package com.huaan9527.mall.webapi.sms;

import com.alibaba.fastjson.JSON;
import com.huaan9527.mall.webapi.configuration.properties.TencentSmsProperties;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TencentSmsProviderAdapter extends AbstractSmsProvider implements InitializingBean {
    private TencentSmsProperties properties;
    private SmsClient smsClient;

    public TencentSmsProviderAdapter(TencentSmsProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Credential cred = new Credential(properties.getSecretId(), properties.getSecretKey());

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        this.smsClient = new SmsClient(cred, properties.getRegion(), clientProfile);
    }

    @Override
    protected SendSmsResponse executeSendSms(SendSmsRequest request) {
        try {
            com.tencentcloudapi.sms.v20190711.models.SendSmsRequest req = buildTencentSendSmsRequest(request);
            log.info("tencent send sms request:{}", JSON.toJSONString(req));

            com.tencentcloudapi.sms.v20190711.models.SendSmsResponse response =
                    smsClient.SendSms(req);
            log.info("tencent send sms response:{}", JSON.toJSONString(response));
            return new SendSmsResponse(200, "发送短信成功");
        } catch (TencentCloudSDKException e) {
            log.error("发送短信失败:{}", e.getMessage(), e);
            return new SendSmsResponse(500, "发送短信失败");
        }
    }


    private com.tencentcloudapi.sms.v20190711.models.SendSmsRequest buildTencentSendSmsRequest(SendSmsRequest request) {
        com.tencentcloudapi.sms.v20190711.models.SendSmsRequest req =
                new com.tencentcloudapi.sms.v20190711.models.SendSmsRequest();

        List<String> phones = new ArrayList<>();
        request.getPhones().forEach(phone -> {
            if (phone.startsWith("+")) {
                phones.add(phone);
            } else {
                phones.add("+86" + phone);
            }
        });

        req.setPhoneNumberSet(phones.toArray(new String[0]));
        req.setTemplateID(request.getTemplateId());
        req.setSmsSdkAppid(properties.getAppId());
        req.setTemplateParamSet(request.getParams().values().toArray(new String[0]));
        req.setSign("小忆智库");
        return req;
    }

}
