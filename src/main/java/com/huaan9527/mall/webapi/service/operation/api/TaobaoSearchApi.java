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
public class TaobaoSearchApi extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/tb-service/get-tb-service";

    public TaobaoSearchApi(DaTaoKeProperties daTaoKeProperties) {
        super(daTaoKeProperties);
    }

    public JSONArray search(String keyword, Integer page, String sort, String hasCoupon) {
        TreeMap<String, String> params = createParams();
        params.put("pageNo", page.toString());
        params.put("pageSize", "50");
        params.put("keyWords", keyword);
        params.put("sort", sort);
        params.put("hasCoupon", hasCoupon);

        String body = ApiClient.sendReq(url, daTaoKeProperties.getAppSecret(), params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("TaobaoSearchApi, body:{},params:{}", body, params);
            return null;
        }
        return result.getJSONArray("data");
    }


}
