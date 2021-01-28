package com.huaan9527.mall.webapi.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dataoke")
public class DaTaoKeProperties {
    private String appKey;
    private String appSecret;

}
