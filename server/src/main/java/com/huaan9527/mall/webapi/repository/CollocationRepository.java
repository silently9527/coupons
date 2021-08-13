package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.Collocation;
import com.huaan9527.mall.webapi.vos.CollocationDetailVo;
import com.huaan9527.mall.webapi.vos.CollocationVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.Modifying;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollocationRepository extends MybatisRepository<Collocation, Long> {
    @Query(statement = "findList")
    Page<CollocationVo> findList(Pageable pageable);

    @Query(statement = "findDetailById")
    CollocationDetailVo findDetailById(@Param("collocationId") Long collocationId);

    @Modifying
    @Query(statement = "updateAppreciateCount")
    void updateAppreciateCount(@Param("collocationId") Long collocationId, @Param("amount") int amount);

    @Query(statement = "simpleDetail")
    CollocationVo simpleDetail(@Param("collocationId") Long collocationId);

    long countBySourceTypeAndSourceId(String sourceType, String sourceId);

    @Query("UPDATE collocation SET view_count = view_count + 1 WHERE id=?1")
    void increaseViewCount(Long collocationId);

    Page<Collocation> findBySex(Integer sex, Pageable pageable);

}