package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;


public interface CollectionRepository extends MybatisRepository<Collection, Long> {
    Page<Collection> findByUserIdAndDataTypeOrderByCreatedDateDesc(Long userId, String dataType, Pageable pageable);

    long countByUserId(Long currentUserId);

    Collection findByUserIdAndDataIdAndDataType(Long userId, String dataId, String dataType);

    @Query("delete from collection where user_id=?1 and data_id=?2 and data_type=?3")
    void removeCollection(Long userId, String dataId, String dataType);
}
