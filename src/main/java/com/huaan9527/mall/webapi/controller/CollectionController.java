package com.huaan9527.mall.webapi.controller;

import com.huaan9527.mall.webapi.domain.enums.DataType;
import com.huaan9527.mall.webapi.service.CollectionService;
import com.huaan9527.mall.webapi.utils.PageResponse;
import com.huaan9527.mall.webapi.utils.ResponseEntity;
import com.huaan9527.mall.webapi.vos.CollectionVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
@AllArgsConstructor
public class CollectionController {

    private CollectionService collectionService;

    @GetMapping("/product/add")
    public ResponseEntity addProductCollection(String productId) {
        collectionService.addCollection(DataType.Product.name(), productId);
        return ResponseEntity.success();
    }

    @GetMapping("/collocation/add")
    public ResponseEntity addCollocationCollection(String collocationId) {
        collectionService.addCollection(DataType.Collocation.name(), collocationId);
        return ResponseEntity.success();
    }

    @GetMapping("/product/remove")
    public ResponseEntity removeProductCollection(String productId) {
        collectionService.removeCollection(DataType.Product.name(), productId);
        return ResponseEntity.success();
    }

    @GetMapping("/collocation/remove")
    public ResponseEntity removeCollocationCollection(String collocationId) {
        collectionService.removeCollection(DataType.Collocation.name(), collocationId);
        return ResponseEntity.success();
    }

    @GetMapping("/product/list")
    public ResponseEntity listProductCollection(Pageable pageable) {
        PageResponse<CollectionVo> response = collectionService.listProductCollection(pageable);
        return ResponseEntity.success(response);
    }

    @GetMapping("/collocation/list")
    public ResponseEntity listCollocationCollection(Pageable pageable) {
        PageResponse<CollectionVo> response = collectionService.listCollocationCollection(pageable);
        return ResponseEntity.success(response);
    }


}
