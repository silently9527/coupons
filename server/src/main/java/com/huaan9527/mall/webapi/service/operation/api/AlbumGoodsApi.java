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
public class AlbumGoodsApi extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/goods/topic/goods-list";

    public AlbumGoodsApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }

    public JSONArray loadAlbumGoods(Integer page, Integer size, String topicId) {
        TreeMap<String, String> params = createParams();
        params.put("pageId", page.toString());
        params.put("pageSize", size.toString());
        params.put("topicId", topicId);
        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("查询专辑失败, body:{}", body);
            return null;
        }
        return result.getJSONArray("data");
    }

}
