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
public class SupperSearchApi extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/goods/list-super-goods";

    public SupperSearchApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }

    public JSONObject search(String keyword, Integer page, Integer sort) {
        TreeMap<String, String> params = createParams();
        params.put("type", "0");
        params.put("keyWords", keyword);
        params.put("pageId", page.toString());
        params.put("pageSize", "50");
        params.put("sort", sort.toString());
        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("SearchApi, body:{}", body);
            return null;
        }
        return result.getJSONObject("data");
    }

}
