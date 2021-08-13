package com.huaan9527.mall.webapi.repository;

import com.huaan9527.mall.webapi.domain.Carousel;
import org.springframework.data.mybatis.repository.MybatisRepository;

import java.util.List;

public interface CarouselRepository extends MybatisRepository<Carousel, Long> {

    List<Carousel> findByStatusOrderByCreatedDateDesc(int status);
}
