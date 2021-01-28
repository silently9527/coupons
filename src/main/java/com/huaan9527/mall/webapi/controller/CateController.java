package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.service.GoodsService;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mi")
@AllArgsConstructor
public class CateController {

    private GoodsService goodsService;

    @GetMapping("/load_cate")
    public ResponseEntity loadCate() {
        return ResponseEntity.success(goodsService.loadCate());
    }



}
