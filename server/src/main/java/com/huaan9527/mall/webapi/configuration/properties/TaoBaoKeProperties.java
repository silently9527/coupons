package com.huaan9527.mall.webapi.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "taobaoke")
public class TaoBaoKeProperties {
    private String serverUrl;
    private String appKey;
    private String appSecret;

}
