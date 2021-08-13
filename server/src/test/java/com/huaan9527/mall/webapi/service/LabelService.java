package com.huaan9527.mall.webapi.service;

public class LabelService implements RemoteLoader {
    @Override
    public String load() {
        this.delay();
        return "标签信息";
    }
}
