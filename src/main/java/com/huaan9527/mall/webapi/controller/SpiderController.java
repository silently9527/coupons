package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.service.SpiderService;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/mi")
public class SpiderController {

    private SpiderService spiderService;

    /**
     * page = 0
     * @param page
     * @return
     */
    @GetMapping("/spider_nothing")
    public ResponseEntity spiderNothing(Integer page, Integer size) {
        Integer successAmount = spiderService.spiderNothingCollocationList(page, size);
        return ResponseEntity.success(successAmount);
    }

    /**
     * page = 1
     * @param page
     * @return
     */
    @GetMapping("/spider_yi_xin")
    public ResponseEntity spiderYiXin(Integer page, Integer size) {
        Integer successAmount = spiderService.spiderYiXinCollocationList(page, size);
        return ResponseEntity.success(successAmount);
    }

}
