package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.CollocationProduct;
import org.springframework.data.mybatis.repository.MybatisRepository;

import java.util.List;

public interface CollocationProductRepository extends MybatisRepository<CollocationProduct, Long> {
    List<CollocationProduct> findByCollocationIdAndProductStatus(Long collocationId, String status);
}
