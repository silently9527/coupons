package com.huaan9527.mall.webapi.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MobServiceClient {
    private static final String comfirmSMSCodeURL = "https://webapi.sms.mob.com/sms/verify";

    @Value("${mob.service.appkey}")
    private String appkey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 校验短信验证码
     *
     * @return
     */
    public Boolean confirmSMSCode(String mobile, String code) {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("appkey", appkey);
        parameter.add("phone", mobile);
        parameter.add("code", code);
        parameter.add("zone", "86");

        String body = restTemplate.postForObject(comfirmSMSCodeURL, parameter, String.class);
        log.error("MobServiceClient.confirmSMSCode => body:{}", body);
        JSONObject result = JSON.parseObject(body);
        Integer status = result.getInteger("status");
        return Integer.valueOf(200).equals(status);
    }


}
