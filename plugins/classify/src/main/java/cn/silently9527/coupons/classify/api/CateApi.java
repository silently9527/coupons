package cn.silently9527.coupons.classify.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtk.main.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Slf4j
@Component
public class CateApi extends AbstractDaTaoKeApi {
    private static final String url = "https://openapi.dataoke.com/api/category/get-super-category";


    public JSONArray loadCate() {
        TreeMap<String, String> params = createParams();
        String body = ApiClient.sendReq(url, "f188932996748f8d6717ec85fc0f76be", params);
        JSONObject result = JSON.parseObject(body);
        if (result.getInteger("code") != 0) {
            log.error("查询分类失败, body:{}", body);
            return null;
        }
        return result.getJSONArray("data");

    }

}
