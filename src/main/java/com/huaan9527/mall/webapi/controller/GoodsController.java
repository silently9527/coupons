package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.service.GoodsService;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.vos.ProductVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mi")
@AllArgsConstructor
public class GoodsController {

    private GoodsService goodsService;

    @GetMapping("/load_recommend_goods")
    public ResponseEntity recommendGoods(Integer page) {
        return ResponseEntity.success(goodsService.recommendGoods(page, 50));
    }

    @GetMapping("/load_nine_goods")
    public ResponseEntity nineGoods(Integer page, Integer size, String nineCid) {
        return ResponseEntity.success(goodsService.nineGoods(page, size, nineCid));
    }

    @GetMapping("/load_rank_goods")
    public ResponseEntity rankGoods(String cid) {
        return ResponseEntity.success(goodsService.rankGoods(cid));
    }

    @GetMapping("/load_goods_by_cate")
    public ResponseEntity loadGoodsByCate(String subcid, Integer page, Integer sort) {
        return ResponseEntity.success(goodsService.loadGoodsByCate(subcid, page, 50, sort));
    }

    @GetMapping("/goods_detail")
    public ResponseEntity goodsDetail(String id, String goodsId) {
        return ResponseEntity.success(goodsService.goodsDetail(id, goodsId, true));
    }

    @GetMapping("/goods_detail2")
    public ResponseEntity goodsDetail2(String id, String goodsId) {
        ProductVo productVo = goodsService.goodsDetail(id, goodsId, true);
        Map<String, String> privilegeLink = goodsService.getPrivilegeLink(productVo.getGoodsId());
        Map<String, Object> data = new HashMap<>();
        data.put("productVo", productVo);
        data.put("privilegeLink", privilegeLink);
        return ResponseEntity.success(data);
    }

    @GetMapping("/get_privilege_link")
    public ResponseEntity getPrivilegeLink(String goodsId) {
        Map<String, String> privilegeLink = goodsService.getPrivilegeLink(goodsId);
        return ResponseEntity.success("Success", privilegeLink);
    }

    @Deprecated
    @GetMapping("/search")
    public ResponseEntity search(String keyword, Integer page, Integer sort) {
        return ResponseEntity.success(goodsService.search(keyword, page, sort));
    }

    @GetMapping("/search2")
    public ResponseEntity search2(String keyword, Integer page, String sort, String hasCoupon) {
        return ResponseEntity.success(goodsService.search2(keyword, page, sort, hasCoupon));
    }

    @GetMapping("/get_hot_search")
    public ResponseEntity getHotSearch() {
        return ResponseEntity.success(goodsService.getHotSearchTop10());
    }


    @GetMapping("/get_similar_goods")
    public ResponseEntity getSimilarGoods(String daTaoKeGoodsId) {
        return ResponseEntity.success(goodsService.similarGoods(daTaoKeGoodsId));
    }


}
