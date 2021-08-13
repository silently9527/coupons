package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.service.CollocationService;
import com.huaan9527.mall.webapi.utils.PageResponse;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.vos.CollocationDetailVo;
import com.huaan9527.mall.webapi.vos.CollocationProductVo;
import com.huaan9527.mall.webapi.vos.CollocationVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
@AllArgsConstructor
public class CollocationController {
    private CollocationService collocationService;

    @GetMapping("/mi/collocation/load_recommend")
    public ResponseEntity recommendCollocations(Integer page) {
        PageRequest request = PageRequest.of(page, 20, Sort.Direction.DESC, "created_date");
        PageResponse<CollocationVo> pageResponse = collocationService.list(request);
        return ResponseEntity.success(pageResponse);
    }

    @GetMapping("/mi/collocation/load_recommend2")
    public ResponseEntity recommendCollocations2(Integer page) {
        PageRequest request = PageRequest.of(page, 20, Sort.Direction.DESC, "view_count");
        PageResponse<CollocationVo> pageResponse = collocationService.list(request);
        return ResponseEntity.success(pageResponse);
    }

    @GetMapping("/mi/collocation/list")
    public ResponseEntity list(@RequestParam(required = false) Integer sex, Integer page) {
        PageRequest request = PageRequest.of(page, 24, Sort.Direction.DESC, "createdDate");
        PageResponse<CollocationVo> pageResponse =  collocationService.simpleList(request, sex);
        return ResponseEntity.success(pageResponse);
    }


    @GetMapping("/collocation/add_appreciate")
    public ResponseEntity addAppreciate(Long collocationId) {
        collocationService.addAppreciate(collocationId);
        return ResponseEntity.success("Success");
    }

    @GetMapping("/collocation/cancel_appreciate")
    public ResponseEntity cancelAppreciate(Long collocationId) {
        collocationService.cancelAppreciate(collocationId);
        return ResponseEntity.success("Success");
    }

    @GetMapping("/mi/collocation/detail")
    public ResponseEntity detail(Long collocationId) {
        CollocationDetailVo detail = collocationService.detail(collocationId);
        return ResponseEntity.success(detail);
    }

    @GetMapping("/mi/collocation/simple_detail")
    public ResponseEntity simpleDetail(Long collocationId) {
        CollocationVo collocationVo = collocationService.simpleDetail(collocationId);
        return ResponseEntity.success(collocationVo);
    }

    @GetMapping("/mi/collocation/product")
    public ResponseEntity collocationProduct(Long collocationId) {
        List<CollocationProductVo> data = collocationService.loadCollocationProduct(collocationId);
        return ResponseEntity.success(data);
    }

}
