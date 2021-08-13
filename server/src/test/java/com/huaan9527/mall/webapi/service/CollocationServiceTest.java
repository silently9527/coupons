package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.BaseTest;
import com.huaan9527.mall.webapi.utils.PageResponse;
import com.huaan9527.mall.webapi.vos.CollocationDetailVo;
import com.huaan9527.mall.webapi.vos.CollocationVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


public class CollocationServiceTest extends BaseTest {
    @Autowired
    private CollocationService collocationService;

    @Test
    public void list() {
        PageResponse<CollocationVo> list = collocationService.list(PageRequest.of(0, 1, Sort.Direction.DESC, "created_date"));
        System.out.println(list);
    }

    @Test
    public void detail() {
        CollocationDetailVo detail = collocationService.detail(1L);
        System.out.println(detail);
    }
}