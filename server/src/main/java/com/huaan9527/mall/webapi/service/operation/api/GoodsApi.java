package com.huaan9527.mall.webapi.service.operation.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtk.main.ApiClient;
import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
public class GoodsApi extends AbstractDaTaoKeApi  {
    private static final String url = "https://openapi.dataoke.com/api/goods/get-goods-list";

    public GoodsApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }


    public JSONObject loadGoods(Map<String,String> params) {
        TreeMap<String, String> params2 = createParams();
        params2.putAll(params);
        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params2);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("查询分类失败, body:{}", body);
            return null;
        }
        return result.getJSONObject("data");

    }


}
