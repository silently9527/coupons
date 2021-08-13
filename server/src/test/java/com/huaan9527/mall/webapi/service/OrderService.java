package com.huaan9527.mall.webapi.service;

public class OrderService implements RemoteLoader {
    @Override
    public String load() {
        this.delay();
        return "订单信息";
    }
}
