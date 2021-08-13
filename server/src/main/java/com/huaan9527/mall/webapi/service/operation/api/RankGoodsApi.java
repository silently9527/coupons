package com.huaan9527.mall.webapi.service.operation.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtk.main.ApiClient;
import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Slf4j
@Component
public class RankGoodsApi extends AbstractDaTaoKeApi {
    private static final String NINE_YUAN_NINE_URL = "https://openapi.dataoke.com/api/goods/get-ranking-list";

    public RankGoodsApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }


    public JSONArray loadRankGoods(String cid) {
        TreeMap<String, String> params = createParams();
        params.put("rankType", "1");
        params.put("cid", cid);

        String body = ApiClient.sendReq(NINE_YUAN_NINE_URL, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("查询榜单商品失败, body:{}", body);
            return null;
        }
        return result.getJSONArray("data");

    }

}
