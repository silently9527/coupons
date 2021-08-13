package com.huaan9527.mall.webapi.service;

public class WatchRecordService implements RemoteLoader {
    @Override
    public String load() {
        this.delay();
        return "观看记录";
    }
}
