package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.AppreciateRelation;
import com.huaan9527.mall.webapi.domain.enums.DataType;
import org.springframework.data.mybatis.repository.Modifying;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;

import java.util.List;

public interface AppreciateRelationRepository extends MybatisRepository<AppreciateRelation, Long> {
    @Modifying
    @Query("DELETE FROM appreciate_relation WHERE user_id=?1 AND data_type=?2 AND data_id=?3")
    void deleteByDataTypeAndDataId(Long userId, DataType dataType, Long dataId);

    List<AppreciateRelation> findByUserIdAndDataType(Long userId, DataType dataType);

    AppreciateRelation findByUserIdAndDataIdAndDataType(Long userId, Long dataId, String dataType);
}
