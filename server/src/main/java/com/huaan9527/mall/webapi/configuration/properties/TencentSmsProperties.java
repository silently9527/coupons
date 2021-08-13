package com.huaan9527.mall.webapi.configuration.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "tencent.sms"
)
@Data
public class TencentSmsProperties {

    private String secretId;
    private String secretKey;
    private String appId;
    private String region;


}
