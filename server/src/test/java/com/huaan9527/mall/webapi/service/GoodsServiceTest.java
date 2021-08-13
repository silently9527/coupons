package com.huaan9527.mall.webapi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huaan9527.mall.webapi.utils.URLUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class GoodsServiceTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    public void recommendGoods() {
        URLUtil.UrlEntity entity = URLUtil.parse("https:\\/\\/item.taobao.com\\/item.htm?spm=a1z0d.6639537.1997196601.663.8a597484bti3H4&id=623949649561\"");
        System.out.println(entity.params);


    }
}