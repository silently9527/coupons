package com.huaan9527.mall.webapi.service.operation.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtk.main.ApiClient;
import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Slf4j
@Component
public class GoodsDetailApi extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/goods/get-goods-details";

    public GoodsDetailApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }


    public JSONObject loadGoodsDetail(String id, String tbGoodsId) {
        TreeMap<String, String> params = createParams();
        if (StringUtils.isNotEmpty(id)) {
            params.put("id", id);
        }
        if (StringUtils.isNotEmpty(tbGoodsId)) {
            params.put("goodsId", tbGoodsId);
        }
        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("查询商品详情失败, body:{}", body);
            return null;
        }
        return result.getJSONObject("data");

    }


}
