package com.huaan9527.mall.webapi.service;

public class LearnRecordService implements RemoteLoader {

    public String load() {
        this.delay();
        return "学习信息";
    }

}
