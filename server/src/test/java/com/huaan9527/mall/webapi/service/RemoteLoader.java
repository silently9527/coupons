package com.huaan9527.mall.webapi.service;

public interface RemoteLoader {

    String load();

    default void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
