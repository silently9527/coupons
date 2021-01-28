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
public class NineGoodsApi extends AbstractDaTaoKeApi {
    private static final String NINE_YUAN_NINE_URL = "https://openapi.dataoke.com/api/goods/nine/op-goods-list";

    public NineGoodsApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }

    public JSONObject loadNineGoods(Integer page, Integer size, String nineCid) {
        TreeMap<String, String> params = createParams();
        params.put("pageId", page.toString());
        params.put("pageSize", size.toString());
        params.put("nineCid", nineCid);

        String body = ApiClient.sendReq(NINE_YUAN_NINE_URL, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("查询9.9商品失败, body:{}", body);
            return null;
        }
        return result.getJSONObject("data");

    }

}
