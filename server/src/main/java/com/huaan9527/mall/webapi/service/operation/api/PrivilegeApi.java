package com.huaan9527.mall.webapi.service.operation.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtk.main.ApiClient;
import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Slf4j
@Component
public class PrivilegeApi extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/tb-service/get-privilege-link";
    private static final String pid = "mm_55354930_1527000006_110255950161";
    public PrivilegeApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }

    public JSONObject getPrivilegeLink(String goodsId) {
        TreeMap<String, String> params = createParams();
        params.put("pid", pid);
        params.put("goodsId", goodsId);
        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("转换链接失败, body:{}", body);
            return null;
        }
        return result.getJSONObject("data");

    }

}
