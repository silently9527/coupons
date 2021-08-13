package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.Tag;
import org.springframework.data.mybatis.repository.MybatisRepository;

public interface TagRepository extends MybatisRepository<Tag, Long> {
    Tag findByName(String tagName);
}
