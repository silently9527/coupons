package com.huaan9527.mall.webapi.service;

import com.alibaba.fastjson.JSONObject;
import com.huaan9527.mall.webapi.domain.AppreciateRelation;
import com.huaan9527.mall.webapi.domain.Collocation;
import com.huaan9527.mall.webapi.domain.CollocationProduct;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import com.huaan9527.mall.webapi.domain.enums.ProductStatus;
import com.huaan9527.mall.webapi.repository.CollocationProductRepository;
import com.huaan9527.mall.webapi.repository.CollocationRepository;
import com.huaan9527.mall.webapi.security.SecurityUtils;
import com.huaan9527.mall.webapi.service.operation.api.GoodsDetailApi;
import com.huaan9527.mall.webapi.utils.PageResponse;
import com.huaan9527.mall.webapi.vos.CollocationDetailVo;
import com.huaan9527.mall.webapi.vos.CollocationProductVo;
import com.huaan9527.mall.webapi.vos.CollocationVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CollocationService {
    private CollocationRepository collocationRepository;
    private CollocationProductRepository collocationProductRepository;
    private GoodsDetailApi goodsDetailApi;
    private AppreciateRelationService appreciateRelationService;

    /**
     * @param pageable
     * @return
     */
    public PageResponse<CollocationVo> list(Pageable pageable) {
        Page<CollocationVo> collocations = collocationRepository.findList(pageable);
        List<AppreciateRelation> appreciates = appreciateRelationService.findAppreciateCollocations();
        Map<Long, List<AppreciateRelation>> group = appreciates.stream().collect(groupingBy(AppreciateRelation::getDataId));
        List<CollocationVo> collocationVos = collocations.getContent().stream().map(collocation -> {
            CollocationVo collocationVo = CollocationVo.builder().build();
            BeanUtils.copyProperties(collocation, collocationVo);
            List<AppreciateRelation> appreciateRelations = group.get(collocation.getCollocationId());
            if (!CollectionUtils.isEmpty(appreciateRelations)) {
                collocationVo.setAppreciate(true);
            }
            return collocationVo;
        }).collect(toList());
        return new PageResponse<>(pageable.getPageNumber(), pageable.getPageSize(), collocations.getTotalElements(), collocationVos);
    }

    /**
     * @param pageable
     * @param sex
     * @return
     */
    public PageResponse<CollocationVo> simpleList(Pageable pageable, Integer sex) {
        Page<Collocation> collocationPage;
        if (Objects.isNull(sex)) {
            collocationPage = collocationRepository.findAll(pageable);
        } else {
            collocationPage = collocationRepository.findBySex(sex, pageable);
        }
        List<CollocationVo> collocationVos = collocationPage.getContent().stream().map(collocation -> {
            CollocationVo collocationVo = CollocationVo.builder().build();
            BeanUtils.copyProperties(collocation, collocationVo);
            collocationVo.setCollocationId(collocation.getId());
            return collocationVo;
        }).collect(toList());
        return new PageResponse<>(pageable.getPageNumber(), pageable.getPageSize(), collocationPage.getTotalElements(), collocationVos);
    }

    /**
     * @return
     */
    public CollocationDetailVo detail(Long collocationId) {
        collocationRepository.increaseViewCount(collocationId);
        CollocationDetailVo detail = collocationRepository.findDetailById(collocationId);
        detail.getProducts().forEach(product -> {
            String tbGoodsId = product.getGoodsId();
            JSONObject jb = goodsDetailApi.loadGoodsDetail(null, tbGoodsId);
            product.setMainPic(jb.getString("mainPic"));
            product.setMarketingMainPic(jb.getString("marketingMainPic"));
            product.setTitle(jb.getString("dtitle"));
            product.setActualPrice(jb.getString("actualPrice"));
            product.setCouponPrice(jb.getString("couponPrice"));
        });
        return detail;
    }

    public List<CollocationProductVo> loadCollocationProduct(Long collocationId) {
        List<CollocationProduct> products = collocationProductRepository
                .findByCollocationIdAndProductStatus(collocationId, ProductStatus.Up.name());
        return products.stream().map(product -> {
            String tbGoodsId = product.getTbGoodsId();
            CollocationProductVo productVo = new CollocationProductVo();
            JSONObject jb = goodsDetailApi.loadGoodsDetail(null, tbGoodsId);
            if (Objects.isNull(jb)) {
                return null;
            }
            productVo.setMainPic(jb.getString("mainPic"));
            productVo.setMarketingMainPic(jb.getString("marketingMainPic"));
            productVo.setTitle(jb.getString("dtitle"));
            productVo.setActualPrice(jb.getString("actualPrice"));
            productVo.setCouponPrice(jb.getString("couponPrice"));
            productVo.setProductId(product.getId());
            productVo.setGoodsId(tbGoodsId);
            return productVo;
        }).filter(Objects::nonNull).collect(toList());
    }

    @Transactional
    public CollocationVo simpleDetail(Long collocationId) {
        collocationRepository.increaseViewCount(collocationId);
        CollocationVo collocationVo = collocationRepository.simpleDetail(collocationId);
        Long userId = SecurityUtils.getCurrentUserId();
        AppreciateRelation appreciateRelation = appreciateRelationService.findAppreciateCollocation(userId, collocationId, DataType.Collocation.name());
        if (Objects.nonNull(appreciateRelation)) {
            collocationVo.setAppreciate(true);
        }
        return collocationVo;
    }

    @Transactional
    public void addAppreciate(Long collocationId) {
        appreciateRelationService.addAppreciate(DataType.Collocation, collocationId);
        collocationRepository.updateAppreciateCount(collocationId, 1);
    }

    @Transactional
    public void cancelAppreciate(Long collocationId) {
        appreciateRelationService.cancelAppreciate(DataType.Collocation, collocationId);
        collocationRepository.updateAppreciateCount(collocationId, -1);
    }

}
