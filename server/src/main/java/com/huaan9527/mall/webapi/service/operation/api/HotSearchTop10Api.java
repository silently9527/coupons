package com.huaan9527.mall.webapi.service.operation.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtk.main.ApiClient;
import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeMap;

@Slf4j
@Component
public class HotSearchTop10Api extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/category/get-top100";

    public HotSearchTop10Api(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }

    public List<Object> loadHotSearch() {
        TreeMap<String, String> params = createParams();
        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("热搜, body:{}", body);
            return null;
        }
        JSONArray array = result.getJSONObject("data").getJSONArray("hotWords");
        return array.subList(0, 10);
    }

}
