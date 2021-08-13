package com.huaan9527.mall.webapi.service;

import com.huaan9527.mall.webapi.domain.Collection;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import com.huaan9527.mall.webapi.repository.CollectionRepository;
import com.huaan9527.mall.webapi.security.SecurityUtils;
import com.huaan9527.mall.webapi.utils.PageResponse;
import com.huaan9527.mall.webapi.vos.CollectionVo;
import com.huaan9527.mall.webapi.vos.CollocationVo;
import com.huaan9527.mall.webapi.vos.ProductVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CollectionService {
    private GoodsService goodsService;
    private CollocationService collocationService;
    private CollectionRepository collectionRepository;

    @Transactional
    public void addCollection(String dataType, String dataId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Collection existCollection = collectionRepository.findByUserIdAndDataIdAndDataType(currentUserId, dataId, dataType);
        if (existCollection != null) {
            log.info("已经收藏，dataId:{}", dataId);
            return;
        }
        Collection collection = new Collection();
        collection.setUserId(currentUserId);
        collection.setDataId(Long.valueOf(dataId));
        collection.setDataType(dataType);
        collectionRepository.save(collection);
    }

    @Transactional
    public void removeCollection(String dataType, String dataId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        collectionRepository.removeCollection(currentUserId, dataId, dataType);
    }

    public PageResponse<CollectionVo> listProductCollection(Pageable pageable) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<Collection> collectionPage = collectionRepository.findByUserIdAndDataTypeOrderByCreatedDateDesc(userId, DataType.Product.name(), pageable);
        List<CollectionVo> list = collectionPage
                .stream()
                .map(getProductCollection())
                .collect(Collectors.toList());
        return new PageResponse<>(pageable.getPageNumber(), pageable.getPageSize(), collectionPage.getTotalElements(), list);
    }

    private Function<Collection, CollectionVo> getProductCollection() {
        return collection -> {
            CollectionVo collectionVo = new CollectionVo(collection);
            ProductVo productVo = goodsService.goodsDetail(null, collection.getDataId().toString(), false);
            collectionVo.setData(productVo);
            return collectionVo;
        };
    }

    public PageResponse<CollectionVo> listCollocationCollection(Pageable pageable) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<Collection> collectionPage = collectionRepository.findByUserIdAndDataTypeOrderByCreatedDateDesc(userId, DataType.Collocation.name(), pageable);
        List<CollectionVo> list = collectionPage
                .stream()
                .map(getCollocationCollection())
                .collect(Collectors.toList());
        return new PageResponse<>(pageable.getPageNumber(), pageable.getPageSize(), collectionPage.getTotalElements(), list);
    }

    private Function<Collection, CollectionVo> getCollocationCollection() {
        return collection -> {
            CollectionVo collectionVo = new CollectionVo(collection);
            CollocationVo collocationVo = collocationService.simpleDetail(collection.getDataId());
            collectionVo.setData(collocationVo);
            return collectionVo;
        };
    }
}
