package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.Menu;
import org.springframework.data.mybatis.repository.MybatisRepository;

import java.util.List;

public interface MenuRepository extends MybatisRepository<Menu, Long> {
    List<Menu> findByStatusOrderByCreatedDateDesc(int status);

    List<Menu> findByStatusOrderBySortAsc(int status);
}
