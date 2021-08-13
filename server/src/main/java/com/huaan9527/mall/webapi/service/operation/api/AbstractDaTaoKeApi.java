package com.huaan9527.mall.webapi.service.operation.api;

import com.huaan9527.mall.webapi.configuration.properties.DaTaoKeProperties;
import com.huaan9527.mall.webapi.utils.Constants;

import java.util.TreeMap;

public abstract class AbstractDaTaoKeApi {
    protected DaTaoKeProperties daTaoKeProperties;

    public AbstractDaTaoKeApi(DaTaoKeProperties daTaoKeProperties) {
        this.daTaoKeProperties = daTaoKeProperties;
    }

    protected TreeMap<String, String> createParams() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("appKey", daTaoKeProperties.getAppKey());
        params.put("version", Constants.DA_TAO_KE_API_VERSION);
        return params;
    }

}
