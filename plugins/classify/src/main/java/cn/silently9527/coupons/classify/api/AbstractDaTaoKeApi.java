package cn.silently9527.coupons.classify.api;

import java.util.TreeMap;

public abstract class AbstractDaTaoKeApi {

    protected TreeMap<String, String> createParams() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("appKey", "5e567e7e64c23");
        params.put("version", "v2.1.2");
        return params;
    }

}
