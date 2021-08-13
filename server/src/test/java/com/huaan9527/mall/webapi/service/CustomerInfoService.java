package com.huaan9527.mall.webapi.service;

public class CustomerInfoService implements RemoteLoader {

    public String load() {
        this.delay();
        return "基本信息";
    }

}
